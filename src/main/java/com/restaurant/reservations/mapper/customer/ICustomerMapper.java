package com.restaurant.reservations.mapper.customer;

import com.restaurant.reservations.models.dto.CustomerDto;
import com.restaurant.reservations.models.entities.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {

    CustomerDto mapEntityToDto(CustomerEntity customerEntity);
    CustomerEntity mapDtoToEntity(CustomerDto customerDto);

    default Optional<CustomerEntity> mapDtoToEntityOptional(
            Optional<CustomerDto> customerDto) {
        return customerDto.map(this::mapDtoToEntity);
    }

    default Optional<CustomerDto> mapEntityToDtoOptional(
            Optional<CustomerEntity> customerEntity) {
        return customerEntity.map(this::mapEntityToDto);
    }

}
