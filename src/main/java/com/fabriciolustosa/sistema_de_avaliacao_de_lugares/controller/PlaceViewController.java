package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/view")
public class PlaceViewController {

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public String listPlaces(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "6") int size,
                             @RequestParam(required = false) String city) {
        if (city != null && !city.isBlank()) {
            model.addAttribute("places", placeService.findByCity(city));
            model.addAttribute("searchCity", city);
            model.addAttribute("searching", true);
            model.addAttribute("topRated", false);
            model.addAttribute("totalPages", 1);
            model.addAttribute("currentPage", 0);
        } else {
            var pageResult = placeService.listAll(PageRequest.of(page, size));
            model.addAttribute("places", pageResult.getContent()); // lista simples
            model.addAttribute("searching", false);
            model.addAttribute("topRated", false);
            model.addAttribute("totalPages", pageResult.getTotalPages());
            model.addAttribute("currentPage", page);
            model.addAttribute("size", size);
        }
        return "places/list";
    }

    @GetMapping("/top-rated")
    public String topRated(Model model) {
        model.addAttribute("places", placeService.getTopRated());
        model.addAttribute("topRated", true);
        model.addAttribute("searching", false);
        model.addAttribute("totalPages", 1);
        model.addAttribute("currentPage", 0);
        return "places/list";
    }

    @GetMapping("/{id}")
    public String placeDetail(@PathVariable Long id, Model model) {
        model.addAttribute("place", placeService.findById(id));
        model.addAttribute("newReview", new Review());
        return "places/detail";
    }

    @GetMapping("/new")
    public String newPlaceForm(Model model) {
        model.addAttribute("place", new Place());
        return "places/form";
    }

    @PostMapping("/new")
    public String createPlace(@ModelAttribute Place place) {
        Place saved = placeService.create(place);
        return "redirect:/view/" + saved.getId();
    }

    @GetMapping("/{id}/edit")
    public String editPlaceForm(@PathVariable Long id, Model model) {
        model.addAttribute("place", placeService.findById(id));
        return "places/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePlace(@PathVariable Long id, @ModelAttribute PlaceUpdateRequestDTO updateRequest) {
        placeService.partialUpdatePlace(id, updateRequest);
        return "redirect:/view/" + id;
    }

    @PostMapping("/{placeId}/reviews")
    public String addReview(@PathVariable Long placeId, @ModelAttribute Review review) {
        placeService.addReview(placeId, review);
        return "redirect:/view/" + placeId;
    }

    @PostMapping("/{placeId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long placeId, @PathVariable Long reviewId) {
        placeService.deleteReview(placeId, reviewId);
        return "redirect:/view/" + placeId;
    }

    @GetMapping("/{placeId}/reviews/{reviewId}/edit")
    public String editReviewForm(@PathVariable Long placeId, @PathVariable Long reviewId, Model model) {
        model.addAttribute("place", placeService.findById(placeId));
        model.addAttribute("review", placeService.findById(placeId).getReviews()
                .stream().filter(r -> r.getId().equals(reviewId)).findFirst().orElseThrow());
        return "places/edit-review";
    }

    @PostMapping("/{placeId}/reviews/{reviewId}/edit")
    public String updateReview(@PathVariable Long placeId, @PathVariable Long reviewId,
                               @ModelAttribute ReviewUpdateRequestDTO updateRequest) {
        placeService.partialUpdateReview(placeId, reviewId, updateRequest);
        return "redirect:/view/" + placeId;
    }

    @PostMapping("/{id}/delete")
    public String deletePlace(@PathVariable Long id) {
        placeService.delete(id);
        return "redirect:/view";
    }
}