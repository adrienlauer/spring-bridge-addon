/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.spring.internal;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import java.lang.reflect.Proxy;
import org.seedstack.shed.exception.Throwing;
import org.springframework.beans.factory.FactoryBean;

class SeedInstanceFactoryBean implements FactoryBean<Object> {
    // using Guice annotation to prevent Spring from complaining
    @com.google.inject.Inject
    private Injector injector;
    private String classname;
    private String qualifier;
    private boolean proxy = true;

    @Override
    public Object getObject() throws Exception {
        if (classname == null) {
            throw new IllegalArgumentException("Property classname is required for SeedFactoryBean");
        } else {
            Class<?> instanceClass = Class.forName(classname);

            if (proxy) {
                // delay underlying instance retrieving via proxy to break circular dependencies problems
                return Proxy.newProxyInstance(SeedInstanceFactoryBean.class.getClassLoader(),
                        new Class<?>[]{instanceClass},
                        new InstanceProxy((Throwing.Supplier<?, Exception>) this::createInstance));
            } else {
                return createInstance();
            }
        }
    }

    @Override
    public Class<?> getObjectType() {
        if (classname == null) {
            return null;
        }

        try {
            return Class.forName(classname);
        } catch (ClassNotFoundException e) { // NOSONAR
            return null;
        }
    }

    private Object createInstance() throws Exception {
        Class<?> instanceClass = Class.forName(classname);
        if (qualifier == null) {
            return injector.getInstance(instanceClass);
        } else {
            return injector.getInstance(Key.get(instanceClass, Names.named(qualifier)));
        }
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }
}
