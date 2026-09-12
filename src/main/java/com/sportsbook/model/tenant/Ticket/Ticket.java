package com.sportsbook.model.tenant.Ticket;

import com.sportsbook.model.tenant.Player.Player;
import com.sportsbook.model.tenant.TicketSelection.TicketSelection;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal stake;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalOdds;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal potentialPayout;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false)
    private LocalDateTime placedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketSelection> selections = new ArrayList<>();
}
