package com.restaurant.reservations.mapper.availableSchedule.Impl;

import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.models.dto.AvailableScheduleDto;
import com.restaurant.reservations.models.entities.AvailableScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class AvailableScheduleMapperImpl implements IAvailableScheduleMapper {

    @Override
    public AvailableScheduleDto mapEntityToDto(AvailableScheduleEntity availableScheduleEntity) {
        var availableScheduleDto = new AvailableScheduleDto();
        availableScheduleDto.setId(availableScheduleEntity.getId());
        availableScheduleDto.setDayAvailable(availableScheduleEntity.getDayAvaliable());
        availableScheduleDto.setHourAvailable(availableScheduleEntity.getHourAvaliable());
        availableScheduleDto.setAvailable(availableScheduleEntity.getAvaliable());
        return availableScheduleDto;
    }

    @Override
    public AvailableScheduleEntity mapDtoToEntity(AvailableScheduleDto availableScheduleDto) {
        var availableScheduleEntity = new AvailableScheduleEntity();
        availableScheduleEntity.setId(availableScheduleDto.getId());
        availableScheduleEntity.setDayAvaliable(availableScheduleDto.getDayAvailable());
        availableScheduleEntity.setHourAvaliable(availableScheduleDto.getHourAvailable());
        availableScheduleEntity.setAvaliable(availableScheduleDto.getAvailable());
        return availableScheduleEntity;
    }
}
