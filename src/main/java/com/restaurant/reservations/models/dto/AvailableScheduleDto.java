package com.restaurant.reservations.models.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AvailableScheduleDto {

    private long id;
    private LocalDate dayAvailable;
    private LocalTime hourAvailable;
    private int available;
}
