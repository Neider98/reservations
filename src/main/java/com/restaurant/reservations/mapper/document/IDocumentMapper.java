package com.restaurant.reservations.mapper.document;

import com.restaurant.reservations.models.dto.DocumentDto;
import com.restaurant.reservations.models.entities.DocumentEntity;

public interface IDocumentMapper {

    DocumentDto mapEntityToDto(DocumentEntity documentEntity);
    DocumentEntity mapDtoToEntity(DocumentDto documentDto);

}
