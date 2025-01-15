package com.restaurant.reservations.mapper.document;

import com.restaurant.reservations.models.dto.DocumentDto;
import com.restaurant.reservations.models.entities.DocumentEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface IDocumentMapper {

    DocumentDto mapEntityToDto(DocumentEntity documentEntity);
    DocumentEntity mapDtoToEntity(DocumentDto documentDto);

    default Optional<DocumentEntity> mapDtoToEntityOptional(
            Optional<DocumentDto> documentDto) {
        return documentDto.map(this::mapDtoToEntity);
    }

    default Optional<DocumentDto> mapEntityToDtoOptional(
            Optional<DocumentEntity> documentEntity) {
        return documentEntity.map(this::mapEntityToDto);
    }
}
