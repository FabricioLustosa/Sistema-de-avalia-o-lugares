package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;


import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaceResponseDTO {//control what the API returns
    private Long id;
    private String name;
    private String city;
    private String description;
    private double averageRating;
    private List<ReviewResponseDTO> reviewResponseDTOS;

    // Construtor que converte a Entidade (Banco de Dados) para o DTO (Resposta da API)
    public PlaceResponseDTO (Place place) {
        // Mapeamos os campos simples diretamente da entidade
        this.id = place.getId();
        this.name = place.getName();
        this.city = place.getCity();
        this.description = place.getDescription();
        this.averageRating = place.getAverageRating();
        
        // Aqui convertemos a lista de Entidades Review para uma lista de DTOs ReviewResponseDTO
        // Isso evita expor a entidade completa e resolve problemas de recursão infinita no JSON
        this.reviewResponseDTOS = place.getReviews()
                .stream()
                .map(ReviewResponseDTO::new) // Para cada Review, cria um novo ReviewResponseDTO
                .toList();
    }

}
