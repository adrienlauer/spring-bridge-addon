/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */

package org.seedstack.spring;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.inject.Named;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.spring.fixtures.autowire.AutowiredService;

@RunWith(SeedITRunner.class)
@WithApplicationContexts("META-INF/spring/autowire-context.xml")
public class SeedBeanFactoryIT {
    @Inject
    @Named("autowireTest")
    private AutowiredService autowiredService;

    @Test
    public void specified_context_should_be_loaded() {
        assertThat(autowiredService.getService1().getFrom()).isEqualTo("seed");
    }
}
