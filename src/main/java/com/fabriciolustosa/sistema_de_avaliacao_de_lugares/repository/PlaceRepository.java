package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
//JpaRepository - Spring Data JPA

    List<Place> findByCity(String city);
}
