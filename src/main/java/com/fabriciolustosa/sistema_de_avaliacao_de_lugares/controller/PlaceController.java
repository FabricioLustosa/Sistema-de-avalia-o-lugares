package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
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
    public List<PlaceResponseDTO> list(){
        return placeService.listAll()
                .stream()
                .map(PlaceResponseDTO::new) // Mapeia cada Place para PlaceResponseDTO
                .toList();
    }

    @GetMapping("/top-rated")
    public List<Place> topRated(){
       return placeService.getTopRated();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        placeService.delete(id);//ao deletar o place, os reviews são deletados automaticamente
    }

    // Endpoint para adicionar uma avaliação a um lugar específico
    @PostMapping("/{placeId}/reviews")
    public Review addReview(@PathVariable Long placeId, @RequestBody Review review) {
       return placeService.addReview(placeId, review);
    }

    // Endpoint para deletar uma avaliação específica de um lugar
    @DeleteMapping("/{placeId}/reviews/{id}")
    public void deleteReview(@PathVariable Long placeId, @PathVariable Long id){
        placeService.deleteReview(placeId, id);
    }
}
