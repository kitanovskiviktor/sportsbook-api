package com.sportsbook.service.user;

import com.sportsbook.dto.user.UserAuthResponse;
import com.sportsbook.dto.user.UserLoginRequest;
import com.sportsbook.model.shared.User.User;
import com.sportsbook.repository.shared.user.UserRepository;
import com.sportsbook.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserAuthResponse login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateAdminToken(
                user.getUsername(), user.getId(),
                user.getRole().name(), user.getTenantKey());

        return new UserAuthResponse(token, user.getUsername(),
                user.getRole().name(), user.getTenantKey());
    }
}
