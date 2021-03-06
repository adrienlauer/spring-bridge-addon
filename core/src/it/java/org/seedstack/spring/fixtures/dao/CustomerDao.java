/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.spring.fixtures.dao;

import org.seedstack.spring.model.Customer;

import java.util.List;

public interface CustomerDao {
    public Customer findById(long id);

    public List<Customer> findAll();

    public void save(Customer customer);

    public void update(Customer customer);

    public void delete(Customer customer);
}
