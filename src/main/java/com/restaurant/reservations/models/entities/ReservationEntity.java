package com.restaurant.reservations.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservaciones")
@Data
public class ReservationEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_horario", nullable = false)
    private AvailableScheduleEntity reservationDate;
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private CustomerEntity customer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdateAt() { this.updateAt = LocalDateTime.now(); }

}
