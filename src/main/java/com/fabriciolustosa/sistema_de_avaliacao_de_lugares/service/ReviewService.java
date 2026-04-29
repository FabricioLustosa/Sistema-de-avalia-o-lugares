package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review create(Review review){
        // [todo] caso queira vc pode adicionar as regras de negocio aqui antes de salvar uma review
        Review reviewSaved = this.repository.save(review);
        return reviewSaved;
    }
}
