package com.sportsbook.dto.ticket;

import com.sportsbook.model.tenant.Ticket.TicketStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private BigDecimal stake;
    private BigDecimal totalOdds;
    private BigDecimal potentialPayout;
    private TicketStatus status;
    private LocalDateTime placedAt;
    private List<TicketSelectionResponseDTO> selections;
}