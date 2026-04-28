package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController//recebe a requisição do Postman
@RequestMapping("/places")
@SuppressWarnings("unused")
public class PlaceController {

  private final PlaceService placeService;

  public PlaceController(PlaceService placeService) {
    this.placeService = placeService;
  }

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> create(@RequestBody Place place){
        Place salvo = placeService.create(place);
    return ResponseEntity.status(HttpStatus.CREATED).body(new PlaceResponseDTO(salvo));
    }

    @GetMapping
    public List<PlaceResponseDTO> list(){
        List<PlaceResponseDTO> response = new ArrayList<>();
        List<Place> places = placeService.listAll();
        for (int i = 0; i < places.size(); i++) {
          response.add(new PlaceResponseDTO(places.get(i)));
        }
        return response;
    }

    @GetMapping("/top-rated")
      public List<PlaceResponseDTO> topRated(){
        List<PlaceResponseDTO> response = new ArrayList<>();
        List<Place> places = placeService.getTopRated();
        for (int i = 0; i < places.size(); i++) {
          response.add(new PlaceResponseDTO(places.get(i)));
        }
        return response;
    }

    @DeleteMapping("/{id}")
      public ResponseEntity<Void> delete(@PathVariable Long id){
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{placeId}/reviews")
      public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long placeId, @RequestBody Review review) {
         Review savedReview = placeService.addReview(placeId, review);
         return ResponseEntity.status(HttpStatus.CREATED).body(new ReviewResponseDTO(savedReview));
    }

    @DeleteMapping("/{placeId}/reviews/{id}")
      public ResponseEntity<Void> deleteReview(@PathVariable Long placeId, @PathVariable Long id){
        placeService.deleteReview(placeId, id);
        return ResponseEntity.noContent().build();
    }
}
