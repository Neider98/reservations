package com.restaurant.reservations.services.customer.Impl;

import com.restaurant.reservations.models.entities.CustomerEntity;
import com.restaurant.reservations.models.entities.DocumentEntity;
import com.restaurant.reservations.repositories.customer.ICustomerRepository;
import com.restaurant.reservations.repositories.document.IDocumentRepository;
import com.restaurant.reservations.services.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private ICustomerRepository customerRepository;
    private IDocumentRepository documentRepository;

    @Override
    public CustomerEntity saveCustomerIfExist(CustomerEntity customer) {

        Optional<CustomerEntity> customerFind =
                customerRepository.findByDocumentNumber(customer.getDocument().getNumber());
        return customerFind.orElseGet(() -> customerRepository.save(customer));
    }
}
