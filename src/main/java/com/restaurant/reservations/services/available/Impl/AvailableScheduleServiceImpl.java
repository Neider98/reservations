package com.restaurant.reservations.services.available.Impl;

import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.repositories.AvailableSchedule.IAvailableScheduleRepository;
import com.restaurant.reservations.services.available.IAvailableScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AvailableScheduleServiceImpl implements IAvailableScheduleService {

    private IAvailableScheduleMapper availableScheduleMapping;
    private IAvailableScheduleRepository availableScheduleRepository;

    @Override
    public List<AvailableScheduleDto> getAvailableScheduleByDay(LocalDate date) {
        var available = 1;
        var listDto = availableScheduleRepository.
                findByDayAvaliableAndAvaliable(date, available);
        return listDto.stream()
                .map(entity -> availableScheduleMapping
                        .mapEntityToDto(entity)).toList();
    }
}
