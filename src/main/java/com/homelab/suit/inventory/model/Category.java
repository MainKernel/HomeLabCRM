package com.homelab.suit.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long workspaceId;

    // Зв'язок із батьківською категорією (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Category parent;

    // Зворотний зв'язок: список дочірніх підкатегорій (One-to-Many)
    // Це дуже зручно для побудови дерева категорій на фронтенді
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Category> children = new ArrayList<>();

//     (Опціонально) Зв'язок з компонентами.
//     Зазвичай достатньо зв'язку зі сторони Item, але якщо треба швидко
//     отримати всі компоненти певної категорії, можна розкоментувати це:
     @OneToMany(mappedBy = "category")
     @JsonIgnore
     @Builder.Default
     private List<Item> items = new ArrayList<>();
}
