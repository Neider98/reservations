package com.restaurant.reservations.repositories.reservation;

import com.restaurant.reservations.models.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByReservationDate_DayAvaliable(LocalDate dayAvaliable);


}
