package com.sportsbook.dto.tree;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SportTreeDTO {
    private Long id;
    private String name;
    private List<CategoryTreeDTO> categories;
}
