package com.restaurant.reservations.repositories.document;

import com.restaurant.reservations.models.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDocumentRepository extends JpaRepository<DocumentEntity,
        Long> {

    Optional<DocumentEntity> getByNumber(String number);
}
