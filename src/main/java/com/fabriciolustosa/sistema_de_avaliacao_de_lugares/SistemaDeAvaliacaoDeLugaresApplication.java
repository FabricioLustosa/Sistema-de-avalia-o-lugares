package com.fabriciolustosa.sistema_de_avaliacao_de_lugares;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SistemaDeAvaliacaoDeLugaresApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeAvaliacaoDeLugaresApplication.class, args);
	}

}
