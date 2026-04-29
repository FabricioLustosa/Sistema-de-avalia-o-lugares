package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)//diz que o lado dono do relacionamento é o review (por conta do tipo da lista)
    //e o campo que faz essa ligacao se chama "place" dentro da classe Review
    //CascadeType.ALL diz que toda operacao feita no Place será replicada automaticamente nos Reviews
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    private String name;
    private String city;
    private String description;

    public double getAverageRating(){
        if(reviews == null || reviews.isEmpty()) return 0;

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);
    }

}
