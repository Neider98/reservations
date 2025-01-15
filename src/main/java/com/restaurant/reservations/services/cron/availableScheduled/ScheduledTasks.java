package com.restaurant.reservations.services.cron.availableScheduled;

import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import com.restaurant.reservations.repositories.AvailableSchedule.IAvailableScheduleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
                avaliableScheduleRepository.findByDayAvailableAfter(today);
        List<AvailableScheduleEntity> avaliableScheduleList =
                new ArrayList<>();
        if (listAvaliable.isPresent() && !listAvaliable.get().isEmpty()) {
            LocalDate lastDate =
                    listAvaliable.get().get(listAvaliable.get().size() - 1).getDayAvailable();
            var differenceDays = ChronoUnit.DAYS.between(today, lastDate);
            if (differenceDays <= 30) {
                createAvailabilityDay(avaliableScheduleList, 1, today.plusDays(differenceDays + 1));
                avaliableScheduleRepository.saveAll(avaliableScheduleList);
            }
        } else {
            for (int i = 0; i < 30; i++) {
                createAvailabilityDay(avaliableScheduleList, i, today);
            }
            avaliableScheduleRepository.saveAll(avaliableScheduleList);
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
        for (int j = 11; j<23; j++) {
            avaliableSchedule = new AvailableScheduleEntity();
            LocalDate futureDate = today.plusDays(i);
            avaliableSchedule.setDayAvailable(futureDate);
            avaliableSchedule.setHourAvailable(LocalTime.of(j, 0));
            avaliableSchedule.setAvailable(1);
            avaliableScheduleList.add(avaliableSchedule);
        }
    }
}
