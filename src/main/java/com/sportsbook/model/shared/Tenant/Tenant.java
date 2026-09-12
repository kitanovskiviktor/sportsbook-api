package com.sportsbook.model.shared.Tenant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tenant")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tenantKey;

    @Column(unique = true, nullable = false)
    private String domain;
    private String brandName;
    private String primaryColor;
    private String logoUrl;
}
