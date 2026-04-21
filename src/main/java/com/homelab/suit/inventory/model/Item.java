package com.homelab.suit.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku; // 1

    @Column(nullable = false)
    private String name; // 2

    private String imageUrl; // 3

    @Column(name = "workspace_id")
    private Long workspaceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitOfMeasure unitOfMeasure; // 4

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 5

    private String packageType; // 6

    @Column(nullable = false)
    private BigDecimal minStock; // 7

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // 8

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> parameters; // 9

    private String serialNumber; // 10

    @Column(columnDefinition = "TEXT")
    private String note; // 13

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    @Column(nullable = false)
    private BigDecimal totalQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE) // Забороняємо Lombok створювати прямий сеттер
    private List<InventoryLog> historyLogs;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemDocument> documents = new ArrayList<>();

    public List<InventoryLog> getHistoryLogs() {
        if (this.historyLogs == null) {
            this.historyLogs = new ArrayList<>();
        }
        return this.historyLogs;
    }

    public void addHistoryLog(InventoryLog log) {
        getHistoryLogs().add(log);
        log.setItem(this);
    }
}