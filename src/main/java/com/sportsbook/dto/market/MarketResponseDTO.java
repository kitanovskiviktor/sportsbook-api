package com.sportsbook.dto.market;

import com.sportsbook.dto.outcome.OutcomeResponseDTO;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarketResponseDTO {
    private Long id;
    private Long eventId;
    private Long marketTypeId;
    private String marketTypeName;
    private List<OutcomeResponseDTO> outcomes;
}
