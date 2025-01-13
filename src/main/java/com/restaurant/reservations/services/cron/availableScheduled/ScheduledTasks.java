package com.restaurant.reservations.services.cron.availableScheduled;

import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import com.restaurant.reservations.repositories.AvailableSchedule.IAvailableScheduleRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private final IAvailableScheduleRepository avaliableScheduleRepository;

    ScheduledTasks(IAvailableScheduleRepository availableScheduleRepository) {
        this.avaliableScheduleRepository = availableScheduleRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateScheduleAvailability() {
        LocalDate today = LocalDate.now();
        var listAvaliable =
                avaliableScheduleRepository.findByDayAvaliableAfter(today);
        List<AvailableScheduleEntity> avaliableScheduleList =
                new ArrayList<>();
        if (listAvaliable.isPresent() && !listAvaliable.get().isEmpty()) {
            LocalDate lastDate =
                    listAvaliable.get().get(listAvaliable.get().size() - 1).getDayAvaliable();
            var differenceDays = ChronoUnit.DAYS.between(lastDate, today);
            createAvailabilityDay(avaliableScheduleList, 1, today.plusDays(differenceDays));
            avaliableScheduleRepository.saveAll(avaliableScheduleList);
        } else {
            for (int i = 0; i < 30; i++) {
                createAvailabilityDay(avaliableScheduleList, i, today);
            }
            avaliableScheduleRepository.saveAll(avaliableScheduleList);  // Guardar después de llenar la lista
        }
    }

    @PostConstruct
    public void initializeOnStartup() {
        updateScheduleAvailability();
    }

    private void createAvailabilityDay(
            List<AvailableScheduleEntity> avaliableScheduleList, int i,
            LocalDate today) {
        AvailableScheduleEntity avaliableSchedule;

        for (int j = 9; j<22; j++) {
            avaliableSchedule = new AvailableScheduleEntity();
            LocalDate futureDate = today.plusDays(i);
            avaliableSchedule.setDayAvaliable(futureDate);
            avaliableSchedule.setHourAvaliable(LocalTime.of(j, 0));
            avaliableSchedule.setAvaliable(1);
            avaliableScheduleList.add(avaliableSchedule);
        }
    }
}
