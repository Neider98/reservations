package com.restaurant.reservations.services.available;

import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.models.entities.AvailableScheduleEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IAvailableScheduleService {

    List<AvailableScheduleDto> getAvailableScheduleByDay(LocalDate date);
    Optional<AvailableScheduleDto> getAvailableScheduleByHour(LocalDate day,
                                                            LocalTime hour);
    AvailableScheduleDto updateAvailableSchedule(AvailableScheduleDto availableScheduleDto);

}
