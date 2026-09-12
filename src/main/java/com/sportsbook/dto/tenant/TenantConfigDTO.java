package com.sportsbook.dto.tenant;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TenantConfigDTO {
    private String tenantKey;
    private String brandName;
    private String primaryColor;
    private String logoUrl;
}