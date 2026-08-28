package com.sportsbook.dto.tree;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventTreeDTO {
    private Long id;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private String status;
}
