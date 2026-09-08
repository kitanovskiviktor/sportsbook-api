package com.sportsbook.dto.auth;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long playerId;
    private String username;
}