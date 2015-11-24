/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.spring.internal;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;

import org.seedstack.seed.SeedException;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;
import org.seedstack.seed.transaction.spi.TransactionalProxy;

import com.google.inject.MembersInjector;

/**
 * 
 * @author redouane.loulou@ext.mpsa.com
 *
 */
public class SpringEntityManagerMembersInjector<T> implements MembersInjector<T> {

	private Field field;
	private ConfigurationProvider configurationProvider;
	private Class<?> currentClass;

	public SpringEntityManagerMembersInjector(Field field, ConfigurationProvider configurationProvider, Class<?> currentClass) {
		this.field = field;
		this.configurationProvider = configurationProvider;
		this.currentClass = currentClass;

	}


	@Override
	public void injectMembers(T instance) {
		try {
			this.field.setAccessible(true);
			field.set(instance,TransactionalProxy.create(EntityManager.class, new SpringEntityManagerLink(configurationProvider, currentClass)));
		} catch (IllegalAccessException e) {
			throw SeedException.wrap(e, SpringErrorCode.UNABLE_TO_INJECT_SPRING_ENTITYMANAGER).put("class", currentClass);
		}
	}

}