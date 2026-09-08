package com.sportsbook.dto.ticket;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketSelectionResponseDTO {
    private Long outcomeId;
    private Long eventId;
    private String homeTeam;
    private String awayTeam;
    private String marketName;
    private String outcomeName;
    private String eventStatus;
    private BigDecimal oddsAtPlacement;
}
