package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;


import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlaceResponseDTO {//control what the API returns
    private Long id;
    private String name;
    private String city;
    private String description;
    private double averageRating;
    private List<ReviewResponseDTO> reviewResponseDTOS;
}
