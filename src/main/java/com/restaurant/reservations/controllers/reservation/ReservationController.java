package com.restaurant.reservations.controllers.reservation;

import com.restaurant.reservations.facade.ReservationFacade;
import com.restaurant.reservations.models.dto.ReservationDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/reservation")
public class ReservationController {

    private ReservationFacade reservationFacade;

    @GetMapping("/{date}")
    public ResponseEntity<List<ReservationDto>> getReservationByDay(
            @PathVariable LocalDate date) {
        var reservation = reservationFacade.getReservationsByDay(date);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping()
    public ResponseEntity<ReservationDto> createReservation(
            @RequestBody ReservationDto reservation) {
        try {
            var reservationResponse =
                    reservationFacade.createReservation(reservation);
            if (reservationResponse.getId() == 0) {
                return ResponseEntity
                        .status(HttpStatus.OK).body(reservationResponse);
            }
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(reservationResponse);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(
            @RequestBody ReservationDto reservationDto,
            @PathVariable long id){
        var reservation = reservationFacade.updateReservation(reservationDto,
                id);
        return reservation == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationDto> deleteReservation(
            @PathVariable long id) {
        var reservation = reservationFacade.deleteReservation(id);
        return reservation == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(reservation);
    }

}
