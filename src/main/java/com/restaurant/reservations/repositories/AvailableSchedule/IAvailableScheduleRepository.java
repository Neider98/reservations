package com.restaurant.reservations.repositories.AvailableSchedule;

import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IAvailableScheduleRepository extends JpaRepository<AvailableScheduleEntity, Long> {

    List<AvailableScheduleEntity> findByDayAvaliableAndAvaliable(LocalDate dayAvaliable, int avaliable);
    Optional<List<AvailableScheduleEntity>> findByDayAvaliableAfter(LocalDate date);

}
