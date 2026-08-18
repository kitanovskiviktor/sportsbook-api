package com.sportsbook.model.Outcome;

import com.sportsbook.model.Market.Market;
import com.sportsbook.model.OutcomeType.OutcomeType;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;

@Entity
@Table(name = "outcome")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "market_id")
    @JsonBackReference("market-outcome")
    private Market market;

    @ManyToOne
    @JoinColumn(name = "outcome_type_id")
    private OutcomeType outcomeType;

    @Column(precision = 10, scale = 2)
    private BigDecimal odds;
}