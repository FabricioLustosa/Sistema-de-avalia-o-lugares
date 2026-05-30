package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @ManyToOne
    @JoinColumn(name="place_id")
    @JsonBackReference//Ignora o campo place na serializacao
    private Place place;

    @ManyToOne
    @JoinColumn(name ="owner_id")
    private User owner;

}
