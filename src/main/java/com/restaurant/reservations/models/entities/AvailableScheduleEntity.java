package com.restaurant.reservations.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_disponibles")
@Data
public class AvailableScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "dia", nullable = false)
    private LocalDate dayAvaliable;
    @Column(name = "hora", nullable = false)
    private LocalTime hourAvaliable;
    @Column(name = "disponible", nullable = false)
    private int avaliable;
    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

}
