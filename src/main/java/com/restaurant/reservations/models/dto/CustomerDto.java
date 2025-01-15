package com.restaurant.reservations.models.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private long id;
    private String firstName;
    private String lastName;
    private DocumentDto document;
    private String phoneNumber;
    private String email;


}
