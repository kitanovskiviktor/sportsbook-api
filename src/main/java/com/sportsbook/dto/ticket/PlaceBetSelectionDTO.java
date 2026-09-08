package com.sportsbook.dto.ticket;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlaceBetSelectionDTO {
    private Long outcomeId;
    private BigDecimal odds;
}
