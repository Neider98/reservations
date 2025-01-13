package com.restaurant.reservations.services.document.Impl;

import com.restaurant.reservations.models.entities.DocumentEntity;
import com.restaurant.reservations.repositories.document.IDocumentRepository;
import com.restaurant.reservations.services.document.IDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DocumentServiceImpl implements IDocumentService {

    private final IDocumentRepository documentRepository;

    @Override
    public DocumentEntity saveDocument(DocumentEntity document) {
        var documentFind = documentRepository.getByNumber(document.getNumber());
        return documentFind.orElseGet(() -> documentRepository.save(document));
    }
}
