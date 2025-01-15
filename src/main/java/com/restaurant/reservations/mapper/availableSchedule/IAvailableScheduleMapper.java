package com.restaurant.reservations.mapper.availableSchedule;

import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IAvailableScheduleMapper {

    AvailableScheduleDto mapEntityToDto(
            AvailableScheduleEntity availableScheduleEntity);
    AvailableScheduleEntity mapDtoToEntity(
            AvailableScheduleDto availableScheduleDto);

    default Optional<AvailableScheduleEntity> mapDtoToEntityOptional(
            Optional<AvailableScheduleDto> availableScheduleDto) {
        return availableScheduleDto.map(this::mapDtoToEntity);
    }

    default Optional<AvailableScheduleDto> mapEntityToDtoOptional(
            Optional<AvailableScheduleEntity> availableScheduleEntity) {
        return availableScheduleEntity.map(this::mapEntityToDto);
    }
}
