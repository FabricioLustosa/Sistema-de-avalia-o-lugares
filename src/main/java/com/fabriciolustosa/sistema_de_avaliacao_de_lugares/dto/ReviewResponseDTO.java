package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponseDTO{
    private Long id;
    private int rating;
    private String comment;

    // O DTO serve para selecionar apenas os dados que queremos enviar para o cliente
    public ReviewResponseDTO(Review review){
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        // Note que não incluímos o objeto 'Place' aqui para evitar loops no JSON
    }

}

