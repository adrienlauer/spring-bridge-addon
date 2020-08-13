package org.seedstack.spring.fixtures;

import javax.inject.Named;
import org.seedstack.seed.Bind;

@Bind(from = Service.class)
@Named("seed")
public class SeedService implements Service {
    @Override
    public String getFrom() {
        return "seed";
    }
}
