package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
