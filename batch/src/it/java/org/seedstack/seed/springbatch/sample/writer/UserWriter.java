/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.seed.springbatch.sample.writer;

import java.util.List;

import org.seedstack.seed.springbatch.sample.domain.User;
import org.seedstack.seed.springbatch.sample.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("userWriter")
public class UserWriter implements ItemWriter<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserWriter.class);

	private UserService userService;

	@Override
	public void write(final List<? extends User> users) throws Exception {

		for (User user : users) {
			logger.debug("Writing user information FirstName: {}, LastName: {}, Email: {}", user.getFirstName(), user.getLastName(), user.getEmail());

			userService.save(user);

		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	

}
