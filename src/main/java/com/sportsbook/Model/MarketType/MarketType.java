package com.sportsbook.Model.MarketType;

import com.sportsbook.Model.OutcomeType.OutcomeType;
import com.sportsbook.Model.Sport.Sport;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market_type")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @OneToMany(mappedBy = "marketType", cascade = CascadeType.ALL)
    @JsonManagedReference("markettype-outcometype")
    private List<OutcomeType> outcomeTypes = new ArrayList<>();
}
