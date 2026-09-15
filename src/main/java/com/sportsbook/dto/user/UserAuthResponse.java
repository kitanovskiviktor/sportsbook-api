package com.sportsbook.dto.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserAuthResponse {
    private String token;
    private String username;
    private String role;
    private String tenantKey;
}
