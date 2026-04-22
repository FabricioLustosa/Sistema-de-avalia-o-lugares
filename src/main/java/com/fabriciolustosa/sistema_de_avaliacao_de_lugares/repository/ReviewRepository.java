package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
