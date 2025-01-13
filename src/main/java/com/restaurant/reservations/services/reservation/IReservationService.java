package com.restaurant.reservations.services.reservation;

import com.restaurant.reservations.models.entities.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public interface IReservationService {

    public ReservationEntity createReservation(ReservationEntity reservation);
    public List<ReservationEntity> getReservationByDay(LocalDate date);
    public ReservationEntity updateReservation(ReservationEntity reservation, long id);
    public ReservationEntity deleteReservation(long id);

}
