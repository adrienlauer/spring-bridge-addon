package org.seedstack.spring.fixtures.autowire;

import org.seedstack.spring.fixtures.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowiredService {
    @Autowired
    private Service service1;

    public Service getService1() {
        return service1;
    }
}
