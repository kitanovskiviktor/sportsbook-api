package com.sportsbook.dto.markettype;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MarketTypeRequestDTO {
    private String name;
    private Long sportId;
    private List<String> outcomeTypes;
}
