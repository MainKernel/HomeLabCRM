package com.homelab.suit.inventory.repository;

import com.homelab.suit.core.model.UserWorkspace;
import com.homelab.suit.core.model.UserWorkspaceId;
import com.homelab.suit.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, UserWorkspaceId> {
    List<UserWorkspace> findAllByUserId(Long id);

    // 1. Шукаємо де юзер є власником
    @Query("SELECT uw FROM UserWorkspace uw WHERE uw.user.id = :userId AND uw.workspaceRole = 'OWNER'")
    List<UserWorkspace> findOwnerWorkspacesByUserId(@Param("userId") Long userId);

    // 2. Шукаємо де юзер є учасником
    @Query("SELECT uw FROM UserWorkspace uw WHERE uw.user.id = :userId AND uw.workspaceRole = 'MEMBER'")
    List<UserWorkspace> findMemberWorkspacesByUserId(@Param("userId") Long userId);

    // 3. Шукаємо всіх учасників конкретного простору.
    @Query("SELECT u FROM UserWorkspace uw JOIN uw.user u WHERE uw.workspace.id = :workspaceId AND uw.workspaceRole = 'MEMBER'")
    List<User> findMembersByWorkspaceId(@Param("workspaceId") Long workspaceId);
}
