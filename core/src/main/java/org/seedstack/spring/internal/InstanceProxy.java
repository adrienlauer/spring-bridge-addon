package org.seedstack.spring.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

final class InstanceProxy implements InvocationHandler {
    private static final Method OBJECT_EQUALS = getObjectMethod("equals", Object.class);
    private final Supplier<?> instanceSupplier;
    private volatile Object instance;

    InstanceProxy(Supplier<?> instanceSupplier) {
        this.instanceSupplier = checkNotNull(instanceSupplier);
    }

    private void initialize() {
        // double checked locking only work if volatile modifier is applied on instance reference
        // see http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = instanceSupplier.get();
                }
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        initialize();

        if (OBJECT_EQUALS.equals(method)) {
            return equalsInternal(args[0]);
        }

        try {
            return method.invoke(instance, args);
        } catch (InvocationTargetException e) { // NOSONAR
            throw e.getCause();
        }
    }

    private boolean equalsInternal(Object other) { // NOSONAR
        // same proxy <==> same underlying object
        if (this == other) {
            return true;
        }

        if (Proxy.isProxyClass(other.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(other);
            if (handler instanceof InstanceProxy) {
                ((InstanceProxy) handler).initialize();
                return ((InstanceProxy) handler).instance.equals(instance);
            } else {
                return false;
            }
        } else {
            return instance.equals(other);
        }
    }

    private static Method getObjectMethod(String name, Class<?>... types) {
        try {
            // null 'types' is OK.
            return Object.class.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
