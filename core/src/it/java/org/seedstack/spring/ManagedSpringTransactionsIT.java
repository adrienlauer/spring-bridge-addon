/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/**
 *
 */
package org.seedstack.spring;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedIT;
import org.seedstack.spring.fixtures.EmService;

/**
 * @author adrien.lauer@mpsa.com
 */
@WithApplicationContexts({"META-INF/spring/MultipleTransactionManagerOrm-context.xml", "META-INF/spring/MultipleTransactionManagerService-context.xml"})
public class ManagedSpringTransactionsIT extends AbstractSeedIT {
    @Inject
    @Named("emService")
    EmService emService;


    @Test
    public void specified_spring_contexts_should_be_loaded() {
        emService.testContactTransaction();
        emService.testUserTransaction();
    }
}
