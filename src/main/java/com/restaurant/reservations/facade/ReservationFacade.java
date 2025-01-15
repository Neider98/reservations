package com.restaurant.reservations.facade;

import com.restaurant.reservations.mapper.availableSchedule.IAvailableScheduleMapper;
import com.restaurant.reservations.mapper.reservation.IReservationMapper;
import com.restaurant.reservations.models.dto.ReservationDto;
import com.restaurant.reservations.services.available.IAvailableScheduleService;
import com.restaurant.reservations.services.customer.ICustomerService;
import com.restaurant.reservations.services.document.IDocumentService;
import com.restaurant.reservations.services.reservation.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class ReservationFacade {

    private ICustomerService customerService;
    private IDocumentService documentService;
    private IAvailableScheduleService availableScheduleService;
    private IAvailableScheduleMapper availableScheduleMapping;
    private IReservationService reservationService;
    private IReservationMapper reservationMapping;

    public ReservationDto createReservation(ReservationDto reservationDto) {

        var reservationEntity = reservationMapping
                .mapDtoToEntity(reservationDto);
        var day = reservationEntity.getReservationDate().getDayAvailable();
        var hour = reservationEntity.getReservationDate().getHourAvailable();

        var availableSchedule =
                (availableScheduleService.getAvailableScheduleByHour(day,
                        hour));
        if (availableSchedule.isEmpty() || availableSchedule.get().getAvailable() != 1) {
           return new ReservationDto();
        }
        reservationEntity.setReservationDate(
                availableScheduleMapping.mapDtoToEntity(availableSchedule.get()));

        var document =
                documentService.saveDocument(reservationEntity.getCustomer().getDocument());
        reservationEntity.getCustomer().setDocument(document);
        var customer = customerService.saveCustomerIfExist(reservationEntity.getCustomer());
        reservationEntity.setCustomer(customer);
        return reservationMapping.mapEntityToDto(reservationService
                        .createReservation(reservationEntity));
    }

    public List<ReservationDto> getReservationsByDay(LocalDate date) {
        return reservationMapping.mapEntitiesToDtos(
                reservationService.getReservationByDay(date));
    }

    public ReservationDto updateReservation(ReservationDto reservation,
                                            long id) {
        return reservationMapping.mapEntityToDto(reservationService.updateReservation(
                        reservationMapping.mapDtoToEntity(reservation),
                        id));
    }

    public ReservationDto deleteReservation(long id) {
        return reservationMapping.mapEntityToDto(
                reservationService.deleteReservation(id));
    }
}
