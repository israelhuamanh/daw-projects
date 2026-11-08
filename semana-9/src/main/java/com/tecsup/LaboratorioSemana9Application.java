package com.tecsup;

import com.tecsup.entity.Role;
import com.tecsup.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LaboratorioSemana9Application {

    public static void main(String[] args) {
        SpringApplication.run(LaboratorioSemana9Application.class, args);
    }

    // Inicializador para garantizar la existencia de los roles requeridos en la guía
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                Role userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
            }
            if (roleRepository.findByName("MODERATOR").isEmpty()) {
                Role modRole = new Role();
                modRole.setName("MODERATOR");
                roleRepository.save(modRole);
            }
        };
    }
}
