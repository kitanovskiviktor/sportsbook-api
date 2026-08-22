package com.sportsbook.dto.outcome;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OutcomeResponseDTO {
    private Long id;
    private Long outcomeTypeId;
    private String outcomeTypeName;
    private BigDecimal odds;
}
