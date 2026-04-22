package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;


import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//intercepta exceções globalmente
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)//define qual exceção usar
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);//retorna o código http e o corpo com o erro
    }
}

