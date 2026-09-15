package com.sportsbook.dto.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateOperatorRequest {
    private String username;
    private String password;
    private String tenantKey;
}
