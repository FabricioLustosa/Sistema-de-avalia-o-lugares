package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.mapper;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;

public class PlaceMapper {
    public static PlaceResponseDTO toDTO (Place place) {
        // Mapeamos os campos simples diretamente da entidade
        PlaceResponseDTO dto = new PlaceResponseDTO();
        dto.setId(place.getId());
        dto.setName(place.getName());
        dto.setCity(place.getCity());
        dto.setDescription(place.getDescription());
        dto.setAverageRating(place.getAverageRating());

        // Aqui convertemos a lista de Entidades Review para uma lista de DTOs ReviewResponseDTO
        // Isso evita expor a entidade completa e resolve problemas de recursão infinita no JSON
        dto.setReviewResponseDTOS(
                place.getReviews()
                        .stream()
                        .map(ReviewMapper::toDTO) // use a ReviewMapper too
                        .toList()
        );

        return dto;
    }
}
