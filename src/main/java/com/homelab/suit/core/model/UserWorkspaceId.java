package com.homelab.suit.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

// 1. Клас для композитного ключа
@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserWorkspaceId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "workspace_id")
    private Long workspaceId;

    // equals та hashCode обов'язкові для @Embeddable ключів
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWorkspaceId that = (UserWorkspaceId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(workspaceId, that.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, workspaceId);
    }
}
