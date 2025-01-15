package com.restaurant.reservations.services.reservation.Impl;

import com.restaurant.reservations.models.entities.ReservationEntity;
import com.restaurant.reservations.repositories.reservation.IReservationRepository;
import com.restaurant.reservations.services.reservation.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    @Override
    public ReservationEntity createReservation(ReservationEntity reservation) {
        var reservationEntity = reservation;
        reservationEntity = reservationRepository.save(reservationEntity);
        return reservationEntity;
    }

    @Override
    public List<ReservationEntity> getReservationByDay(LocalDate date) {
        return reservationRepository.findByReservationDateDayAvailable(date);
    }

    @Override
    public ReservationEntity updateReservation(ReservationEntity reservation,
                                          long id) {
        Optional<ReservationEntity> reservationFind = reservationRepository.findById(id);
        if (reservationFind.isPresent()) {
            reservationRepository.save(reservation);
            return reservation;
        }
        return new ReservationEntity();
    }

    @Override
    public ReservationEntity deleteReservation(long id) {
        Optional<ReservationEntity> reservation =
                reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
            return reservation.get();
        }
        return new ReservationEntity();
    }

}
