package com.restaurant.reservations.models.dto;

import lombok.Data;

@Data
public class ReservationDto {

    private long id;
    private AvailableScheduleDto reservationDate;
    private CustomerDto customer;
    private String status;
}
