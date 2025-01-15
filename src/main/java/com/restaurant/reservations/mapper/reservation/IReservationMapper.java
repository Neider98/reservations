package com.restaurant.reservations.mapper.reservation;

import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.models.dto.ReservationDto;
import com.restaurant.reservations.models.entities.ReservationEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {
        IAvailableScheduleMapper.class,
        IReservationMapper.class
})
public interface IReservationMapper {

    ReservationDto mapEntityToDto(ReservationEntity reservationEntity);
    ReservationEntity mapDtoToEntity(ReservationDto reservationDTO);

    default List<ReservationDto> mapEntitiesToDtos(
            List<ReservationEntity> reservationEntities) {
        return reservationEntities.stream()
                .map(this::mapEntityToDto).toList();
    }

    default List<ReservationEntity> mapDtosToEntities(
            List<ReservationDto> reservationDtos) {
        return reservationDtos.stream()
                .map(this::mapDtoToEntity).toList();
    }


    default Optional<ReservationEntity> mapDtoToEntityOptional(
            Optional<ReservationDto> reservationDto) {
        return reservationDto.map(this::mapDtoToEntity);
    }

    default Optional<ReservationDto> mapEntityToDtoOptional(
            Optional<ReservationEntity> reservationEntity) {
        return reservationEntity.map(this::mapEntityToDto);
    }
}
