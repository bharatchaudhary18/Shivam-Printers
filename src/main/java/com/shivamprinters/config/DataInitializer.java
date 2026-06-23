package com.shivamprinters.config;

import com.shivamprinters.entity.Role;
import com.shivamprinters.entity.User;
import com.shivamprinters.entity.enums.RoleName;
import com.shivamprinters.repository.RoleRepository;
import com.shivamprinters.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;

    @Override
    public void run(String... args) {
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {
        if (userRepository.findByEmail(appProperties.getAdmin().getEmail()).isPresent()) {
            return;
        }
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found in database"));

        User admin = User.builder()
                .firstName("Admin")
                .lastName("User")
                .email(appProperties.getAdmin().getEmail())
                .password(passwordEncoder.encode(appProperties.getAdmin().getPassword()))
                .roles(Set.of(adminRole))
                .build();

        userRepository.save(admin);
        log.info("Default admin user created: {}", admin.getEmail());
    }
}
