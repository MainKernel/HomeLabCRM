package com.homelab.suit.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "inventory_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogType changeType; // IN, OUT, AUDIT

    @Column(nullable = false)
    private BigDecimal quantityChanged;

    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    private String note;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}