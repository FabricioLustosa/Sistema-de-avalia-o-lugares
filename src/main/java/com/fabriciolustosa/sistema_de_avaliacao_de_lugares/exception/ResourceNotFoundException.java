package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.exception;

public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
}
