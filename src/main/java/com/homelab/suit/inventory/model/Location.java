package com.homelab.suit.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "locations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Наприклад: "Стелаж 1", "Коробка з ESP32"

    private Long workspaceId;

    private String description;

    // Для ієрархії (Склад -> Шафа -> Полиця). Поки що робимо просто
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Location parent;

}
