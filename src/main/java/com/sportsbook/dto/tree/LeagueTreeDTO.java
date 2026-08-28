package com.sportsbook.dto.tree;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeagueTreeDTO {
    private Long id;
    private String name;
    private List<EventTreeDTO> events;
}