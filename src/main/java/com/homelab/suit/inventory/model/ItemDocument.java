package com.homelab.suit.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private OffsetDateTime uploadedAt;

    /**
     * Життєвий цикл JPA: автоматично встановлюємо дату завантаження перед збереженням
     */
    @PrePersist
    protected void onCreate() {
        this.uploadedAt = OffsetDateTime.now();
    }
}