package com.sportsbook.dto.league;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeagueRequestDTO {
    private String name;
    private Long sportId;
    private Long categoryId;
}
