package com.first.firstservice.repositories;

import com.first.firstservice.entities.Customer;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="fullCustomer",types = Customer.class)
public interface CustomerProjection {
    public Long getId();
    public String getName();
}
