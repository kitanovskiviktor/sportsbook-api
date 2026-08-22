package com.sportsbook.dto.markettype;

import com.sportsbook.dto.outcometype.OutcomeTypeResponseDTO;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarketTypeResponseDTO {
    private Long id;
    private String name;
    private Long sportId;
    private String sportName;
    private List<OutcomeTypeResponseDTO> outcomeTypes;
}
