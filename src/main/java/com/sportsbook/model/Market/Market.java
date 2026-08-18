package com.sportsbook.model.Market;

import com.sportsbook.model.Event.Event;
import com.sportsbook.model.MarketType.MarketType;
import com.sportsbook.model.Outcome.Outcome;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference("event-market")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "market_type_id")
    private MarketType marketType;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @JsonManagedReference("market-outcome")
    private List<Outcome> outcomes = new ArrayList<>();
}