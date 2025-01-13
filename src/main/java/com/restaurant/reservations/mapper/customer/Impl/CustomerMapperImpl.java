package com.restaurant.reservations.mapper.customer.Impl;

import com.restaurant.reservations.mapper.customer.ICustomerMapper;
import com.restaurant.reservations.mapper.document.IDocumentMapper;
import com.restaurant.reservations.models.dto.CustomerDto;
import com.restaurant.reservations.models.entities.CustomerEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomerMapperImpl implements ICustomerMapper {

    private IDocumentMapper documentMapper;

    @Override
    public CustomerDto mapEntityToDto(CustomerEntity customerEntity) {
        var customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setFirstName(customerEntity.getFirstName());
        customerDto.setLastName(customerEntity.getLastName());
        customerDto.setPhoneNumber(customerEntity.getPhoneNumber());
        customerDto.setDocument(documentMapper.mapEntityToDto(customerEntity.getDocument()));
        return customerDto;
    }

    @Override
    public CustomerEntity mapDtoToEntity(CustomerDto customerDto) {
        var customer = new CustomerEntity();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setDocument(documentMapper.mapDtoToEntity(customerDto.getDocument()));
        return customer;
    }
}
