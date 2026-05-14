package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.PlaceUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.ReviewUpdateRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String createPlace(@ModelAttribute Place place, Authentication authentication) {
        Place saved = placeService.create(place, authentication);
        return "redirect:/view/" + saved.getId();
    }

    @GetMapping("/{id}/edit")
    public String editPlaceForm(@PathVariable Long id, Model model) {
        model.addAttribute("place", placeService.findById(id));
        return "places/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePlace(@PathVariable Long id, @ModelAttribute PlaceUpdateRequestDTO updateRequest, Authentication authentication) {
        placeService.partialUpdatePlace(id, updateRequest, authentication);
        return "redirect:/view/" + id;
    }

    @PostMapping("/{placeId}/reviews")
    public String addReview(@PathVariable Long placeId, @ModelAttribute Review review) {
        placeService.addReview(placeId, review);
        return "redirect:/view/" + placeId;
    }

    @PostMapping("/{placeId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long placeId, @PathVariable Long reviewId, Authentication authentication) {
        placeService.deleteReview(placeId, reviewId, authentication);
        return "redirect:/view/" + placeId;
    }

    @GetMapping("/{placeId}/reviews/{reviewId}/edit")
    public String editReviewForm(@PathVariable Long placeId, @PathVariable Long reviewId, Model model) {
        Place place = placeService.findById(placeId);

        Review review = place.getReviews()
                        .stream()
                        .filter(r -> r.getId().equals(reviewId))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Review not found"));


        model.addAttribute("place", placeId);
        model.addAttribute("review", review);

        return "places/edit-review";
    }

    @PostMapping("/{placeId}/reviews/{reviewId}/edit")
    public String updateReview(@PathVariable Long placeId, @PathVariable Long reviewId,
                               @ModelAttribute ReviewUpdateRequestDTO updateRequest, Authentication authentication) {
        placeService.partialUpdateReview(placeId, reviewId, updateRequest, authentication);
        return "redirect:/view/" + placeId;
    }

    @PostMapping("/{id}/delete")
    public String deletePlace(@PathVariable Long id, Authentication authentication) {
        placeService.delete(id, authentication);
        return "redirect:/view";
    }
}