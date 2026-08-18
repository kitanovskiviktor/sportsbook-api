package com.sportsbook.Model.Sport;

import com.sportsbook.Model.Category.Category;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sport")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    @JsonManagedReference("sport-category")
    private List<Category> categories = new ArrayList<>();
}
