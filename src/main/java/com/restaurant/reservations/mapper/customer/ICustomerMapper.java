package com.restaurant.reservations.mapper.customer;

import com.restaurant.reservations.models.dto.CustomerDto;
import com.restaurant.reservations.models.entities.CustomerEntity;

public interface ICustomerMapper {

    CustomerDto mapEntityToDto(CustomerEntity customerEntity);
    CustomerEntity mapDtoToEntity(CustomerDto customerDto);

}
