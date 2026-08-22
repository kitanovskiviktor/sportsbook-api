package com.sportsbook.dto.market;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OddInputDTO {
    private Long outcomeTypeId;
    private BigDecimal odds;
}
