package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

      @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

      public void addReview(Review review) {
        reviews.add(review);
        review.setPlace(this);
      }

      public void removeReview(Review review) {
        reviews.remove(review);
        review.setPlace(null);
      }

}
