package com.fabriciolustosa.sistema_de_avaliacao_de_lugares;

import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.entities.User;
import com.fabriciolustosa.sistema_de_avaliacao_de_lugares.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DemoUserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("demo").isEmpty()) {
            User demo = new User();
            demo.setUsername("demo");
            demo.setPassword(passwordEncoder.encode("demo123"));
            userRepository.save(demo);
        }
    }
}
