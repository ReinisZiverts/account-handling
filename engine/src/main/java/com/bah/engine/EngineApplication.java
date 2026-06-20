package com.bah.engine;

import com.bah.engine.entity.User;
import com.bah.engine.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EngineApplication {

    static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (_args) -> {
            if (repository.count() == 0) {
                User user1 = new User();
                user1.setUsername("user1");
                user1.setPassword("password123");
                repository.save(user1);
                User user2 = new User();
                user2.setUsername("user2");
                user2.setPassword("password123");
                repository.save(user2);
                System.out.println("Initial data saved to H2 database.");
            } else {
                System.out.println("Database already contains data, skipping initialization.");
            }
        };
    }
}
