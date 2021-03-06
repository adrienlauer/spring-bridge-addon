/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.spring.internal;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;
import org.seedstack.seed.transaction.spi.TransactionalProxy;

import javax.persistence.EntityManager;

class SpringJpaModule extends AbstractModule {
    private ConfigurationProvider configurationProvider;

    SpringJpaModule(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    @Override
    protected void configure() {
        bind(EntityManager.class).toInstance(TransactionalProxy.create(EntityManager.class, null));
        bindListener(Matchers.any(), new SpringEntityManagerTypeListener(configurationProvider));
    }
}
