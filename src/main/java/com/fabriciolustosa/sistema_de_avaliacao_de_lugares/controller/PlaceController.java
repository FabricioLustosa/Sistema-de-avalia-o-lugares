package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceResponseDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService; // ← usa o Service, não o Repository diretamente

    @PostMapping
    public Place create(@RequestBody Place place){
        return placeService.create(place);//ao salvar o place, os reviews são salvos automaticamente
    }

    @GetMapping
    public List<PlaceResponseDTO> list(){
        return placeService.listAll()
                .stream()
                .map(PlaceResponseDTO::new)
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

    @PostMapping("/{placeId}/reviews")
    public Review addReview(@PathVariable Long placeId, @RequestBody Review review) {
       return placeService.addReview(placeId, review);
    }

    @DeleteMapping("/{placeId}/reviews/{id}")
    public void deleteReview(@PathVariable Long placeId, @PathVariable Long id){
        placeService.deleteReview(placeId, id);
    }
}
