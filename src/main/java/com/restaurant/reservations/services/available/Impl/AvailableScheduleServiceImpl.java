package com.restaurant.reservations.services.available.Impl;

import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import com.restaurant.reservations.repositories.AvailableSchedule.IAvailableScheduleRepository;
import com.restaurant.reservations.services.available.IAvailableScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class AvailableScheduleServiceImpl implements IAvailableScheduleService {

    private IAvailableScheduleMapper availableScheduleMapping;
    private IAvailableScheduleRepository availableScheduleRepository;

    @Override
    public List<AvailableScheduleDto> getAvailableScheduleByDay(LocalDate date) {
        var available = 1;
        var listDto = availableScheduleRepository.
                findByDayAvailableAndAvailable(date, available);
        return listDto.stream()
                .map(entity -> availableScheduleMapping
                        .mapEntityToDto(entity)).toList();
    }

    @Override
    public Optional<AvailableScheduleDto> getAvailableScheduleByHour(LocalDate day, LocalTime hour) {
        var available = availableScheduleRepository.
                findByDayAvailableAndHourAvailable(day, hour);
        return availableScheduleMapping.mapEntityToDtoOptional(available);
    }

    @Override
    public AvailableScheduleDto updateAvailableSchedule(AvailableScheduleDto availableScheduleDto) {
        var availableScheduleEntity = availableScheduleMapping.mapDtoToEntity(availableScheduleDto);
        Optional<AvailableScheduleEntity> availableSchedule =
                availableScheduleRepository.findById(availableScheduleEntity.getId());
        if (availableSchedule.isPresent()) {
            availableScheduleEntity.setAvailable(availableSchedule.get().getAvailable());
            availableScheduleRepository.save(availableScheduleEntity);
            return availableScheduleMapping.mapEntityToDto(availableScheduleEntity);
        }
        return availableScheduleMapping.mapEntityToDto(availableSchedule.get());
    }
}
