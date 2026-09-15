package com.sportsbook.config;

import com.sportsbook.model.shared.User.Role;
import com.sportsbook.model.shared.User.User;
import com.sportsbook.repository.shared.user.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByUsername("superadmin")) {
            User admin = new User();
            admin.setUsername("superadmin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.SUPER_ADMIN);
            admin.setTenantKey(null);
            userRepository.save(admin);
            System.out.println("Created default superadmin (username: superadmin, password: admin)");
        }
    }
}
