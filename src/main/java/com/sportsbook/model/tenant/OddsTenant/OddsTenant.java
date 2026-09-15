package com.sportsbook.model.tenant.OddsTenant;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "odds_tenant")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OddsTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long outcomeId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal overriddenOdds;
}
