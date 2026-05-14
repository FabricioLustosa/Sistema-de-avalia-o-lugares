package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
