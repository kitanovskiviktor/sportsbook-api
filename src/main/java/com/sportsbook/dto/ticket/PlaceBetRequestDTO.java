package com.sportsbook.dto.ticket;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlaceBetRequestDTO {
    private BigDecimal stake;
    private List<PlaceBetSelectionDTO> selections;
}
