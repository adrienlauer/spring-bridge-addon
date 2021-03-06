/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.spring.batch.sample.service;

import javax.inject.Inject;

import org.seedstack.spring.batch.sample.domain.Contact;
import org.seedstack.spring.batch.sample.domain.ContactDao;


public class ContactServiceImpl implements ContactService {

	@Inject
	private ContactDao contactDao;

	@Override
	public Contact save(Contact entity) {
		return contactDao.save(entity);
	}

}
