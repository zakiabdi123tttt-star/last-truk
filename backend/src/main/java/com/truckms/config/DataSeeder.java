package com.truckms.config;

import com.truckms.entity.*;
import com.truckms.repository.MaterialRepository;
import com.truckms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

// FIICHID: init.sql horeba wuu geliyaa admin + materials marka Docker uu bilaabmo
// markii ugu horeysa. DataSeeder-kan waa xakameyn dheeraad ah - haddii xogtu maqan
// tahay (tusaale: database la nadiifiyay), wuu dib u abuurayaa.
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("System Admin")
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);
            System.out.println(">>> Admin default waa la abuuray: admin / admin123");
        }

        if (materialRepository.count() == 0) {
            List<String> defaults = List.of("Caro", "Dhagax", "Jay", "Ciid", "Quruurux", "Sibidh", "Others");
            defaults.forEach(name -> materialRepository.save(Material.builder().name(name).build()));
            System.out.println(">>> Materials default waa la abuuray");
        }
    }
}
