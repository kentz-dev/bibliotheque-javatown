package com.javatown.library.config;

import com.javatown.library.model.Prepose;
import com.javatown.library.repository.PreposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PreposeRepository preposeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (preposeRepository.findByEmail("admin@javatown.com").isEmpty()) {
            Prepose admin = new Prepose("Admin", "Library", "admin@javatown.com", "admin123", "P001");
            preposeRepository.save(admin);
            System.out.println("[DATA_INITIALIZER] Compte préposé par défaut créé : admin@javatown.com / admin123");
        }
    }
}
