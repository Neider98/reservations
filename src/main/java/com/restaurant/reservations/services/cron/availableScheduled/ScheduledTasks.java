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
        var listAvailable = avaliableScheduleRepository.findByDayAvailableAfter(today);
        
        if (listAvailable.isPresent() && !listAvailable.get().isEmpty()) {
            if (!isDayComplete(today)) {
                fillDay(today);
            }
            
            for (int i = 1; i <= 30; i++) {
                LocalDate futureDate = today.plusDays(i);
                if (!isDayComplete(futureDate)) {
                    fillDay(futureDate);
                }
            }
        } else {
            for (int i = 0; i < 30; i++) {
                LocalDate futureDate = today.plusDays(i);
                fillDay(futureDate);
            }
        }
    }

    @PostConstruct
    public void initializeOnStartup() {
        updateScheduleAvailability();
    }

    private boolean isDayComplete(LocalDate date) {
        for (int hour = 11; hour <= 23; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            if (!avaliableScheduleRepository.findByDayAvailableAndHourAvailable(date, time).isPresent()) {
                return false;
            }
        }
        return true;
    }

    private void fillDay(LocalDate date) {
        List<AvailableScheduleEntity> availableScheduleList = new ArrayList<>();
        for (int hour = 11; hour <= 23; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            if (!avaliableScheduleRepository.findByDayAvailableAndHourAvailable(date, time).isPresent()) {
                AvailableScheduleEntity availableSchedule = new AvailableScheduleEntity();
                availableSchedule.setDayAvailable(date);
                availableSchedule.setHourAvailable(time);
                availableSchedule.setAvailable(1);
                availableScheduleList.add(availableSchedule);
            }
        }
        avaliableScheduleRepository.saveAll(availableScheduleList);
    }
}
