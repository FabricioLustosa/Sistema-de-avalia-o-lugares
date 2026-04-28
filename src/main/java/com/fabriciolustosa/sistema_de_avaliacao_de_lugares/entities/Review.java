package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name="place_id")
    @JsonBackReference//Ignora o campo place na serializacao
    private Place place;

}
