package com.restaurant.reservations.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "clientes")
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombres", nullable = false)
    private String firstName;
    @Column(name = "apellidos")
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_documento_identidad", nullable = false)
    private DocumentEntity document;
    @Column(name = "numero_telefono", nullable = false)
    private String phoneNumber;
    @Column(name = "correo_electronico")
    private String email;
    @OneToMany( mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }

}
