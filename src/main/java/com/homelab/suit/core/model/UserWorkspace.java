package com.homelab.suit.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homelab.suit.inventory.model.Workspace;
import com.homelab.suit.security.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_workspaces")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserWorkspace {

    @EmbeddedId
    @Builder.Default
    private UserWorkspaceId id = new UserWorkspaceId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workspaceId")
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(name = "workspace_role", nullable = false)
    private String workspaceRole;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private OffsetDateTime joinedAt;

    @PrePersist
    protected void onCreate() {
        this.joinedAt = OffsetDateTime.now();
    }
}
