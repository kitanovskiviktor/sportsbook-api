package com.sportsbook.model.OutcomeType;

import com.sportsbook.model.MarketType.MarketType;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "outcome_type")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OutcomeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "market_type_id")
    @JsonBackReference("markettype-outcometype")
    private MarketType marketType;
}
