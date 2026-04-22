package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

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

    public void delete(@PathVariable Long id){
        placeRepository.deleteById(id);//ao deletar o place, os reviews são deletados automaticamente
    }

    public Review addReview(@PathVariable Long placeId, @RequestBody Review review) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));



        review.setPlace(place);
        place.getReviews().add(review);
        placeRepository.save(place);

        return review;
    }
}
