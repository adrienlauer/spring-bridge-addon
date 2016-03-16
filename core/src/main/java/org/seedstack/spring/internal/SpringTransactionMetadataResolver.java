/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.spring.internal;

import org.seedstack.seed.core.utils.SeedReflectionUtils;
import org.seedstack.spring.SpringTransactionManager;
import org.seedstack.seed.transaction.spi.TransactionMetadata;
import org.seedstack.seed.transaction.spi.TransactionMetadataResolver;
import org.aopalliance.intercept.MethodInvocation;

/**
 * This {@link org.seedstack.seed.transaction.spi.TransactionMetadataResolver} resolves metadata for transactions marked
 * with {@link SpringTransactionManager}.
 *
 * @author adrien.lauer@mpsa.com
 */
public class SpringTransactionMetadataResolver implements TransactionMetadataResolver {
    @Override
    public TransactionMetadata resolve(MethodInvocation methodInvocation, TransactionMetadata defaults) {
        SpringTransactionManager springTransactionManager = SeedReflectionUtils.getMetaAnnotationFromAncestors(methodInvocation.getMethod(), SpringTransactionManager.class);

        if (springTransactionManager != null) {
            TransactionMetadata result = new TransactionMetadata();
            result.setHandler(SpringTransactionHandler.class);
            result.setResource(springTransactionManager.value());
            return result;
        }

        return null;
    }
}