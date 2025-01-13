package com.restaurant.reservations.mapper.reservation.Impl;

import com.restaurant.reservations.mapper.customer.ICustomerMapper;
import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.mapper.reservation.IReservationMapper;
import com.restaurant.reservations.models.dto.ReservationDto;
import com.restaurant.reservations.models.entities.ReservationEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ReservationMapperImpl implements IReservationMapper {

    private ICustomerMapper customerMapper;
    private IAvailableScheduleMapper availableScheduleMapper;

    @Override
    public ReservationEntity mapDtoToEntity(ReservationDto reservationDTO){
        var reservation = new ReservationEntity();
        reservation.setId(reservationDTO.getId());
        reservation.setReservationDate(availableScheduleMapper.mapDtoToEntity(reservationDTO.getReservationDate()));
        reservation.setCustomer(customerMapper.mapDtoToEntity(reservationDTO.getCustomer()));
        return reservation;
    }

    @Override
    public List<ReservationDto> mapEntitiesToDtos(List<ReservationEntity> reservations) {
        return reservations.stream().map(this::mapEntityToDto).toList();
    }

    @Override
    public List<ReservationEntity> mapDtosToEntities(List<ReservationDto> reservations) {
        return reservations.stream().map(this::mapDtoToEntity).toList();
    }

    @Override
    public ReservationDto mapEntityToDto(ReservationEntity reservationEntity){
        var reservation = new ReservationDto();
        reservation.setId(reservationEntity.getId());
        reservation.setReservationDate(availableScheduleMapper.mapEntityToDto(reservationEntity.getReservationDate()));
        reservation.setCustomer(customerMapper.mapEntityToDto(reservationEntity.getCustomer()));
        return reservation;
    }
}
