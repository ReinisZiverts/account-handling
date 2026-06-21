package com.bah.engine;

import com.bah.engine.entity.User;
import com.bah.engine.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository, PasswordEncoder passwordEncoder) {
        return (_args) -> {
            if (repository.count() == 0) {
                User user1 = new User();
                user1.setUsername("user1");
                user1.setPassword(passwordEncoder.encode("password123"));
                user1.setRole("USER");
                repository.save(user1);
                User user2 = new User();
                user2.setUsername("user2");
                user2.setPassword(passwordEncoder.encode("password123"));
                user2.setRole("USER");
                repository.save(user2);
            }
        };
    }
}
