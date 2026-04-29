package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.PlaceRepository;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .toList();//converte o stream de volta pra lista
    }

    public void delete(Long id){
        placeRepository.deleteById(id);//ao deletar o place, os reviews são deletados automaticamente
    }

    public Review addReview(Long placeId, Review review) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        placeRepository.save(place);

        return review;
    }

    public void deleteReview(Long placeId, Long id){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));
    }
}
