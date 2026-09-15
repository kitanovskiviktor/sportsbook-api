package com.sportsbook.dto.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OperatorResponse {
    private Long id;
    private String username;
    private String tenantKey;
}
