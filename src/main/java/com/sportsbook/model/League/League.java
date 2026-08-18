package com.sportsbook.model.League;

import com.sportsbook.model.Category.Category;
import com.sportsbook.model.Sport.Sport;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "league")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference("category-league")
    private Category category;
}