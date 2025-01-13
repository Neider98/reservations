package com.restaurant.reservations.services.available;

import com.restaurant.reservations.models.dto.AvailableScheduleDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAvailableScheduleService {

    List<AvailableScheduleDto> getAvailableScheduleByDay(LocalDate date);

}
