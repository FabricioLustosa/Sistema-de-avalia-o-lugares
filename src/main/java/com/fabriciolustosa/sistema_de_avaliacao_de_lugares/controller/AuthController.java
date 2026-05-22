package com.fabriciolustosa.sistema_de_avaliacao_de_lugares.controller;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.dto.RegisterRequestDTO;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.User;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequestDTO data){
        String username = data.getUsername().trim();
        System.out.println(repository.findAll());
        if(repository.findByUsername(username).isPresent()){
            return "redirect:/register?error";
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(encoder.encode(data.getPassword()));


        repository.save(user);

        return "redirect:/view";
    }
}
