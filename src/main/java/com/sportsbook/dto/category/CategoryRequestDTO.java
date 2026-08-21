package com.sportsbook.dto.category;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategoryRequestDTO {
    private String name;
    private Long sportId;
}
