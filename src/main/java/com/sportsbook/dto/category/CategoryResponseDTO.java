package com.sportsbook.dto.category;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private Long sportId;
    private String sportName;
}