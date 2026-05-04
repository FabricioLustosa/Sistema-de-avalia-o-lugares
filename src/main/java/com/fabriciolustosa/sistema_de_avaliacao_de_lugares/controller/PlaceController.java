package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.mapper.ReviewMapper;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//recebe a requisição do Postman
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService; // ← usa o Service, não o Repository diretamente

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> create(@RequestBody Place place){
        Place salvo = placeService.create(place);
        return ResponseEntity.status(201).body(new PlaceResponseDTO(salvo));//ao salvar o place, os reviews são salvos automaticamente
    }

    // Endpoint para listar todos os lugares convertidos para DTO
    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> list(){
        List<PlaceResponseDTO>places = placeService.listAll()
                .stream()
                .map(PlaceResponseDTO::new) // Mapeia cada Place para PlaceResponseDTO
                .toList();

        return ResponseEntity.ok(places);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<PlaceResponseDTO>> topRated(){
             List<PlaceResponseDTO> places = placeService.getTopRated()
               .stream()
               .map(PlaceResponseDTO::new)
               .toList();

        return ResponseEntity.ok(places);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaceResponseDTO> findById(@PathVariable Long id){
        Place place = placeService.findById(id);
        return ResponseEntity.ok(new PlaceResponseDTO(place));
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
                .body(new PlaceResponseDTO(place));
    }
}
