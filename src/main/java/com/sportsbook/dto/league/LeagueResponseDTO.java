package com.sportsbook.dto.league;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeagueResponseDTO {
    private Long id;
    private String name;
    private Long sportId;
    private String sportName;
    private Long categoryId;
    private String categoryName;
}
