package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.*;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.mapper.PlaceMapper;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.mapper.ReviewMapper;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//recebe a requisição do Postman
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService; // ← usa o Service, não o Repository diretamente

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> create(@Valid @RequestBody Place place){
        Place salved = placeService.create(place);
        return ResponseEntity.status(201).body(PlaceMapper.toDTO(salved));//ao salvar o place, os reviews são salvos automaticamente
    }

    // Endpoint para listar todos os lugares convertidos para DTO
    @GetMapping
    public ResponseEntity<Page<PlaceResponseDTO>> list(Pageable pageable){

        return ResponseEntity.ok(
                placeService.listAll(pageable)
                        .map(PlaceMapper::toDTO)
        );
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<PlaceResponseDTO>> topRated(){
             List<PlaceResponseDTO> places = placeService.getTopRated()
               .stream()
               .map(PlaceMapper::toDTO)
               .toList();

        return ResponseEntity.ok(places);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceResponseDTO> findById(@PathVariable Long id){
        Place place = placeService.findById(id);
        return ResponseEntity.ok(PlaceMapper.toDTO(place));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaceResponseDTO>> searchByCiyt(@RequestParam String city){
        return ResponseEntity.ok(
                placeService.findByCity(city)
                    .stream()
                    .map(PlaceMapper::toDTO)
                    .toList()
                );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        placeService.delete(id);//ao deletar o place, os reviews são deletados automaticamente
        return ResponseEntity.noContent().build(); //204 not found
    }

    // Endpoint para adicionar uma avaliação a um lugar específico
    @PostMapping("/{placeId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long placeId, @Valid @RequestBody Review review) {
       Review savedReview = placeService.addReview(placeId, review);

        return ResponseEntity.status(201).body(ReviewMapper.toDTO(savedReview));
    }

    // Endpoint para deletar uma avaliação específica de um lugar
    @DeleteMapping("/{placeId}/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long placeId, @PathVariable Long id){
        placeService.deleteReview(placeId, id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> partialUpdatePlace(@PathVariable Long id, @Valid @RequestBody PlaceUpdateRequestDTO updateRequest){
        Place place = placeService.partialUpdatePlace(id, updateRequest);
        return ResponseEntity.ok()
                .body(PlaceMapper.toDTO(place));
    }

    @PatchMapping("/{placeId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> partialUpdateReview(@PathVariable Long placeId, @PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateRequestDTO updateRequest){
        Review review = placeService.partialUpdateReview(placeId, reviewId, updateRequest);
        return ResponseEntity.ok()
                .body(ReviewMapper.toDTO(review));
    }
}
