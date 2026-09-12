package com.sportsbook.model.tenant.TicketSelection;

import com.sportsbook.model.tenant.Ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ticket_selection")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TicketSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    private Long outcomeId;
    private Long eventId;
    private String homeTeam;
    private String awayTeam;
    private String marketName;
    private String outcomeName;
    private String eventStatus;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal oddsAtPlacement;
}
