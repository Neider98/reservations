package com.restaurant.reservations.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "documentos_identidad")
@Data
public class DocumentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "numero_documento", nullable = false)
    private String number;
    @Column(name = "tipo_documento", nullable = false)
    private String type;
    @Column(name = "pais_expedicion")
    private String expeditionCountry;
    @Column(name = "fecha_expedicion")
    private LocalDate issueDate;
    private LocalDateTime createAt;

    @PrePersist
    public void setCreateAt() {
        this.createAt = LocalDateTime.now();
    }


}
