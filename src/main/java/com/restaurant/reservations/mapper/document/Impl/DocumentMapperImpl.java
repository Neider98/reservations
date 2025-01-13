package com.restaurant.reservations.mapper.document.Impl;

import com.restaurant.reservations.mapper.document.IDocumentMapper;
import com.restaurant.reservations.models.dto.DocumentDto;
import com.restaurant.reservations.models.entities.DocumentEntity;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapperImpl implements IDocumentMapper {

    @Override
    public DocumentDto mapEntityToDto(DocumentEntity documentEntity) {
        var documentDto = new DocumentDto();
        documentDto.setId(documentEntity.getId());
        documentDto.setNumber(documentEntity.getNumber());
        documentDto.setType(documentEntity.getType());
        documentDto.setExpeditionCountry(documentEntity.getExpeditionCountry());
        documentDto.setIssueDate(documentEntity.getIssueDate());
        return documentDto;
    }

    @Override
    public DocumentEntity mapDtoToEntity(DocumentDto documentDto) {
        var document = new DocumentEntity();
        document.setId(documentDto.getId());
        document.setNumber(documentDto.getNumber());
        document.setType(documentDto.getType());
        document.setExpeditionCountry(documentDto.getExpeditionCountry());
        document.setIssueDate(documentDto.getIssueDate());
        return document;
    }
}
