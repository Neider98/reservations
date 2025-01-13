package com.restaurant.reservations.repositories.customer;

import com.restaurant.reservations.models.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByDocumentNumber(String documentNumber);


}
