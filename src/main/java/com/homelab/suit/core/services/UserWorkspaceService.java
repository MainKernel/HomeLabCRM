package com.homelab.suit.core.services;

import com.homelab.suit.core.dto.WorkspaceDto;
import com.homelab.suit.core.model.UserWorkspace;
import com.homelab.suit.core.repository.UserWorkspaceRepository;
import com.homelab.suit.inventory.model.Workspace;
import com.homelab.suit.security.User;
import com.homelab.suit.security.exception.UserNotFound;
import com.homelab.suit.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWorkspaceService {
    private final UserWorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    public List<WorkspaceDto> getUserWorkspaces(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserNotFound("User with email " + email + " not found"));

        return workspaceRepository.findAllByUserId(user.getId()).stream().map(this::mapToDto).toList();
    }

    private WorkspaceDto mapToDto(UserWorkspace workspace){
        return new WorkspaceDto(
                workspace.getId().getWorkspaceId(),
                workspace.getWorkspace().getName(),
                workspace.getWorkspaceRole()
        );
    }
}
