package com.homelab.suit.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homelab.suit.core.model.UserWorkspace;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workspace")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<UserWorkspace> users = new ArrayList<>();
}
