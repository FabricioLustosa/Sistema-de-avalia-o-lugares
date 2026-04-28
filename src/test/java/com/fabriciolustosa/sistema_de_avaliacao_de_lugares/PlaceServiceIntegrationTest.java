package com.fabriciolustosa.sistema_de_avaliacao_de_lugares;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Review;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class PlaceServiceIntegrationTest {

    @Autowired
    private PlaceService placeService;

    @Test
    void shouldCreatePlaceAddReviewAndCalculateAverage() {
        Place place = new Place();
        place.setName("Parque Central");
        place.setCity("São Paulo");
        place.setDescription("Espaço público para caminhadas e lazer");

        Place savedPlace = placeService.create(place);
        assertNotNull(savedPlace.getId());
        assertEquals(0.0, savedPlace.getAverageRating());

        Review review = new Review();
        review.setRating(5);
        review.setComment("Excelente lugar");

        Review savedReview = placeService.addReview(savedPlace.getId(), review);
        assertNotNull(savedReview.getId());

            Place reloadedPlace = null;
            for (Place item : placeService.listAll()) {
              if (item.getId().equals(savedPlace.getId())) {
                reloadedPlace = item;
                break;
              }
            }
            if (reloadedPlace == null) {
              throw new IllegalStateException("Place not found after save");
            }

        assertEquals(1, reloadedPlace.getReviews().size());
        assertEquals(5.0, reloadedPlace.getAverageRating());
    }
}


