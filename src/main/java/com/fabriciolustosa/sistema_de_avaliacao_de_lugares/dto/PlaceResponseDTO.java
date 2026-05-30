package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDTO {//control what the API returns
    //only what the client should see
    private Long id;
    private String name;
    private String city;
    private String description;
    private String ownerUsername;
    private double averageRating;
    private List<ReviewResponseDTO> reviewResponseDTOS;

}
