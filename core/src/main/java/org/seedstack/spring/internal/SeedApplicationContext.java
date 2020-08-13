package org.seedstack.spring.internal;

import io.nuun.kernel.api.di.UnitModule;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SeedApplicationContext extends GenericApplicationContext implements UnitModule {
    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

    public SeedApplicationContext(String... resources) {
        super(new SeedBeanFactory());
        this.reader.loadBeanDefinitions(resources);
    }

    public SeedApplicationContext(ApplicationContext parent, String... resources) {
        super(new SeedBeanFactory(), parent);
        this.reader.loadBeanDefinitions(resources);
    }

    @Override
    public Object nativeModule() {
        return null;
    }

    @Override
    public <T> T as(Class<T> targetType) {
        return null;
    }
}
