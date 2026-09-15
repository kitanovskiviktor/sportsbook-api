package com.sportsbook.dto.user;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BrandUpdateRequest {
    private String brandName;
    private String primaryColor;
    private String logoUrl;
}
