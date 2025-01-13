package com.restaurant.reservations.mapper.availableSchedule;

import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.models.entities.AvailableScheduleEntity;

import java.util.Optional;

public interface IAvailableScheduleMapper {
    AvailableScheduleDto mapEntityToDto(
            AvailableScheduleEntity availableScheduleEntity);
    AvailableScheduleEntity mapDtoToEntity(
            AvailableScheduleDto availableScheduleDto);
}
