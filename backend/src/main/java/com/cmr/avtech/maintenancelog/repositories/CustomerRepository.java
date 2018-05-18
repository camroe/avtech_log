package com.cmr.avtech.maintenancelog.repositories;

import com.cmr.avtech.maintenancelog.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface CustomerRepository extends CrudRepository<Customer, Long> {
        List<Customer> findByLastName(String lastName);
    }
