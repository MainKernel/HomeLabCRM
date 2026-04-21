package com.homelab.suit.core.repository;

import com.homelab.suit.core.model.UserWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
    List<UserWorkspace> findAllByUserId(Long id);
}
