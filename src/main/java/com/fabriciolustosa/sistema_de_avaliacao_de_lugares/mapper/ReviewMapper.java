package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.mapper;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;

public class ReviewMapper {
    private ReviewMapper(){}
    public static ReviewResponseDTO toDTO (Review review){
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        return dto;
    }
}
