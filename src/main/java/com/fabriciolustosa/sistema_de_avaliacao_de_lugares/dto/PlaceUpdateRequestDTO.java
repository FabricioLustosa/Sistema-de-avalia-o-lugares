package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceUpdateRequestDTO {
    private String name;
    private String description;
    private String city;
}
