package org.seedstack.spring.internal;

import com.google.inject.Injector;
import com.google.inject.Key;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.ResolvableType;

public class SeedBeanFactory extends DefaultListableBeanFactory {
    @Inject
    private Injector injector;

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor, String requestingBeanName,
            Set<String> autowiredBeanNames, TypeConverter typeConverter) throws BeansException {
        try {
            return super.resolveDependency(descriptor, requestingBeanName, autowiredBeanNames, typeConverter);
        } catch (NoSuchBeanDefinitionException e) {
            ResolvableType resolvableType = e.getResolvableType();
            if (resolvableType != null) {
                Key<?> key = Key.get(resolvableType.getType());
                return injector.getInstance(key);
            }
            return null;
        }
    }
}
