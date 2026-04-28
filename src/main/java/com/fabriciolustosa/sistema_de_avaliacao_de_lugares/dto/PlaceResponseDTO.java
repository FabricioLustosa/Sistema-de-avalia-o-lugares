package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;


import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.ArrayList;
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

    public PlaceResponseDTO (Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.description = place.getDescription();
        this.city = place.getCity();
        this.averageRating = place.getAverageRating();
            this.reviewResponseDTOS = new ArrayList<>();
            if (place.getReviews() == null) {
              this.reviewResponseDTOS = Collections.emptyList();
            } else {
              for (int i = 0; i < place.getReviews().size(); i++) {
                this.reviewResponseDTOS.add(new ReviewResponseDTO(place.getReviews().get(i)));
              }
            }
    }

}
