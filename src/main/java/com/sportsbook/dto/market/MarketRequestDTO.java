package com.sportsbook.dto.market;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarketRequestDTO {
    private Long eventId;
    private Long marketTypeId;
    private List<OddInputDTO> odds;
}
