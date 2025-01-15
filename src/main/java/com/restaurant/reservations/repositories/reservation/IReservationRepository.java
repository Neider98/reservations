package com.restaurant.reservations.repositories.reservation;

import com.restaurant.reservations.models.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByReservationDateDayAvailable(LocalDate dayAvaliable);


}
