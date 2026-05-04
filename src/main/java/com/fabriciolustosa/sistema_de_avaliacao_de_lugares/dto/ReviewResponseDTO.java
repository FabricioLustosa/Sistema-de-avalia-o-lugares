package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO{
    private Long id;
    private int rating;
    private String comment;

}

