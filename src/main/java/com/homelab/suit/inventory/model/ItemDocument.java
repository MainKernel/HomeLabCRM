package com.homelab.suit.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "item_documents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
    private Item item;

    @Column(nullable = false)
    private String title; // Назва файлу для відображення користувачу

    @Column(name = "file_url", nullable = false, length = 500)
    private String fileUrl; // Шлях до файлу на диску або посилання

    @Column(nullable = false)
    private String fileUuid;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private OffsetDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        this.uploadedAt = OffsetDateTime.now();
    }
}