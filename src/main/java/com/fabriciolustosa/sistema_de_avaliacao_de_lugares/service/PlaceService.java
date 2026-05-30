package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.*;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.User;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ForbiddenException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.PlaceRepository;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.ReviewRepository;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

//essa camada service é a camada da lógica de negócio: valida, processa e chama o repository

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    private void validateOwner(Place place, User user){
        if(!place.getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("Access denied");
        }
    }

    private User getAuthenticatedUser(Authentication authentication){
        if(authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new AccessDeniedException("Invalid authentication");
    }

    public Page<Place> listAll(Pageable pageable){
        return placeRepository.findAll(pageable);
    }

    public Place create(Place place, Authentication authentication){

        User user = getAuthenticatedUser(authentication);

        place.setOwner(user);
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

    public List<Place> findByCity(String city){
        return placeRepository.findByCity(city);
    }

    public void delete(Long id, Authentication authentication){

        Place place = placeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Place not found"));

        User user = getAuthenticatedUser(authentication);
        validateOwner(place, user);
        placeRepository.delete(place);
    }

    public Review addReview(Long placeId, Review review) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        review.setOwner(user);
        review.setPlace(place);
        place.getReviews().add(review);
        placeRepository.save(place);

        return review;
    }

    @Transactional
    public void deleteReview(Long placeId, Long id, Authentication authentication){
        // Primeiro, verificamos se o lugar (Place) existe
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found"));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

       if(!review.getPlace().getId().equals(placeId)){
           throw new ResourceNotFoundException("Review does not belong to this place");
       }

       User user = getAuthenticatedUser(authentication);

       if(!review.getOwner().getId().equals((user.getId()))){
           throw new ForbiddenException("Access denied");
       }

       place.getReviews().remove(review);//o elemento filho é excluido ao salvar o elemento pai
    }

    public Place partialUpdatePlace(Long placeId, PlaceUpdateRequestDTO updateRequest, Authentication authentication){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found!"));

        User user = getAuthenticatedUser(authentication);
        validateOwner(place, user);

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

    public Review partialUpdateReview(Long placeId, Long reviewId, ReviewUpdateRequestDTO updateRequest, Authentication authentication){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found!"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found!"));

        User user = getAuthenticatedUser(authentication);
        if(!review.getOwner().getId().equals(user.getId())){
            throw new ForbiddenException("Access denied");
        }

        if(!review.getPlace().getId().equals(placeId)){
            throw new ResourceNotFoundException("Review does not belong to this place!");
        }

        if(updateRequest.getComment() != null){
            review.setComment(updateRequest.getComment());
        }
        if(updateRequest.getRating() != null){
            review.setRating(updateRequest.getRating());
        }

        return reviewRepository.save(review);
    }
}
