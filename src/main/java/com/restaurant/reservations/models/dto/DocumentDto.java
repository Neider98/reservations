package com.restaurant.reservations.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentDto {

    private long id;
    private String number;
    private String type;
    private String expeditionCountry;
    private LocalDate issueDate;

}
