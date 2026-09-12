package com.sportsbook.service.player;

import com.sportsbook.dto.auth.*;
import com.sportsbook.model.tenant.Player.Player;
import com.sportsbook.repository.tenant.player.PlayerRepository;
import com.sportsbook.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public PlayerService(PlayerRepository playerRepository,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        if (playerRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (playerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Player player = new Player();
        player.setUsername(request.getUsername());
        player.setEmail(request.getEmail());
        player.setPassword(passwordEncoder.encode(request.getPassword()));
        player.setBalance(BigDecimal.ZERO);

        Player saved = playerRepository.save(player);
        String token = jwtUtil.generateToken(saved.getUsername(), saved.getId());
        return new AuthResponse(token, saved.getId(), saved.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        Player player = playerRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), player.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(player.getUsername(), player.getId());
        return new AuthResponse(token, player.getId(), player.getUsername());
    }
}