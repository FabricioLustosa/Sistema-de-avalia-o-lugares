package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String password;
}
