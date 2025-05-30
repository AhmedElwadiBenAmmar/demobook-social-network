package com.wadi.booknetwork;

import com.wadi.booknetwork.role.Role;
import com.wadi.booknetwork.role.RoleRepository;
import jdk.jfr.Enabled;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            // Ici, tu peux ajouter le code à exécuter au démarrage de l'application,
            // par exemple pour initialiser des rôles en base de données.




            if(roleRepository.findByName("USER").isEmpty()) {

            roleRepository.save(
                    Role.builder().name("USER").build());

            }


            System.out.println("Initialisation des rôles terminée.");
        };
    }

}


