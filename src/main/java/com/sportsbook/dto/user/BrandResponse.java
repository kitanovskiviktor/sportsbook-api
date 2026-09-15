package com.sportsbook.dto.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String tenantKey;
    private String domain;
    private String brandName;
    private String primaryColor;
    private String logoUrl;
}
