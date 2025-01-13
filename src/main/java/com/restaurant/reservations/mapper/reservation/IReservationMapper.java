package com.restaurant.reservations.mapper.reservation;

import com.restaurant.reservations.models.dto.ReservationDto;
import com.restaurant.reservations.models.entities.ReservationEntity;

import java.util.List;

public interface IReservationMapper {

    ReservationDto mapEntityToDto(ReservationEntity reservationEntity);
    ReservationEntity mapDtoToEntity(ReservationDto reservationDTO);
    List<ReservationDto> mapEntitiesToDtos(List<ReservationEntity> reservations);
    List<ReservationEntity> mapDtosToEntities(List<ReservationDto> reservations);

}
