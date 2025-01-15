package com.restaurant.reservations.repositories.AvailableSchedule;

import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IAvailableScheduleRepository extends JpaRepository<AvailableScheduleEntity, Long> {

    List<AvailableScheduleEntity> findByDayAvailableAndAvailable(LocalDate dayAvaliable, int avaliable);
    Optional<List<AvailableScheduleEntity>> findByDayAvailableAfter(LocalDate date);
    Optional<AvailableScheduleEntity> findByDayAvailableAndHourAvailable(LocalDate dayAvaliable
            , LocalTime hour);
}
