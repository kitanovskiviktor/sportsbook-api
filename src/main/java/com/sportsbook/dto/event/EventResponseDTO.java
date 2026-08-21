package com.sportsbook.dto.event;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private String status;
    private Long leagueId;
    private String leagueName;
}
