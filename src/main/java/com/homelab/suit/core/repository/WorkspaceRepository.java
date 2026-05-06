package com.homelab.suit.core.repository;

import com.homelab.suit.inventory.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
