package com.sportsbook.dto.oddsTenant;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OddsTenantRequest {
    private Long outcomeId;
    private BigDecimal overriddenOdds;
}
