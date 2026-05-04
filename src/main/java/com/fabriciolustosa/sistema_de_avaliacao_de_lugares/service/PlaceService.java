package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.PlaceRepository;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//essa camada service é a camada da lógica de negócio: valida, processa e chama o repository

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Place> listAll(){
        return placeRepository.findAll();
    }

    public Place create(Place place){
        return placeRepository.save(place);
    }

    public List<Place> getTopRated(){
        return placeRepository.findAll()
                .stream()
                .sorted((p1, p2) -> Double.compare(
                        p2.getAverageRating(),
                        p1.getAverageRating()
                ))
                .toList();//converte o stream de volta pra lista
    }

    public Place findById(Long id){
        //Place place = placeRepository.findById(id);//returns an Optional<Place>, not a Place directly
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found with id: " + id));
    }

    public void delete(Long id){
        placeRepository.deleteById(id);//ao deletar o place, os reviews são deletados automaticamente
    }

    public Review addReview(Long placeId, Review review) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));



        review.setPlace(place);
        place.getReviews().add(review);
        placeRepository.save(place);

        return review;
    }

    // Método para deletar um review específico de um lugar
    @Transactional
    public void deleteReview(Long placeId, Long id){
        // Primeiro, verificamos se o lugar (Place) existe
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

       if(!review.getPlace().getId().equals(placeId)){
           throw new ResourceNotFoundException("Review does not belong to this place");
       }

       place.getReviews().remove(review);//o elemento filho é excluido ao salvar o elemento pai
    }

    public Place partialUpdatePlace(Long placeId, PlaceUpdateRequestDTO updateRequest){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found!"));

        if(updateRequest.getName() != null){
            place.setName(updateRequest.getName());
        }
        if(updateRequest.getDescription() != null){
            place.setDescription(updateRequest.getDescription());
        }
        if(updateRequest.getCity() != null){
            place.setCity(updateRequest.getCity());
        }

        return placeRepository.save(place);
    }
}
