package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.PlaceRepository;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//essa camada service é a camada da lógica de negócio: valida, processa e chama o repository

@Service
public class PlaceService {

  private final PlaceRepository placeRepository;
  private final ReviewRepository reviewRepository;

  public PlaceService(PlaceRepository placeRepository, ReviewRepository reviewRepository) {
    this.placeRepository = placeRepository;
    this.reviewRepository = reviewRepository;
  }

    public List<Place> listAll(){
        return placeRepository.findAll();
    }

    public Place create(Place place){
    if (place.getReviews() != null) {
      place.getReviews().forEach(review -> review.setPlace(place));
    }
        return placeRepository.saveAndFlush(place);
    }

    public List<Place> getTopRated(){
        List<Place> places = placeRepository.findAll();
        Collections.sort(places, new Comparator<Place>() {
          @Override
          public int compare(Place p1, Place p2) {
            return Double.compare(p2.getAverageRating(), p1.getAverageRating());
          }
        });
        return places;
    }

    public void delete(Long id){
        Place place = placeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Place not found"));
        placeRepository.delete(place);
    }

    public Review addReview(Long placeId, Review review) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        place.addReview(review);
        return reviewRepository.saveAndFlush(review);
    }

    public void deleteReview(Long placeId, Long id){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        Review review = place.getReviews().stream()
            .filter(item -> item.getId() != null && item.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        place.removeReview(review);
            reviewRepository.delete(review);
            reviewRepository.flush();
    }
}
