package com.restaurant.reservations.services.customer;

import com.restaurant.reservations.models.entities.CustomerEntity;

public interface ICustomerService {

    CustomerEntity saveCustomerIfExist(CustomerEntity customer);

}
