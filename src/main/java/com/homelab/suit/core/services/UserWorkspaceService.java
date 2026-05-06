package com.homelab.suit.core.services;

import com.homelab.suit.core.dto.WorkspaceDto;
import com.homelab.suit.core.dto.WorkspaceUserDto;
import com.homelab.suit.core.model.UserWorkspace;
import com.homelab.suit.core.repository.WorkspaceRepository;
import com.homelab.suit.inventory.exceptions.WorkspaceNotFoundException;
import com.homelab.suit.inventory.model.Workspace;
import com.homelab.suit.inventory.repository.UserWorkspaceRepository;
import com.homelab.suit.security.User;
import com.homelab.suit.security.exception.UserNotFound;
import com.homelab.suit.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserWorkspaceService {

    private final UserWorkspaceRepository userWorkspaceRepository; // Для зв'язків та логіки ролей
    private final WorkspaceRepository workspaceRepository;         // ТІЛЬКИ для сутності простору
    private final UserRepository userRepository;

    public List<WorkspaceDto> getUserWorkspaces(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User with email " + email + " not found"));

        return userWorkspaceRepository.findAllByUserId(user.getId())
                .stream().map(this::mapToDto).toList();
    }

    private WorkspaceDto mapToDto(UserWorkspace workspace) {
        return new WorkspaceDto(
                workspace.getId().getWorkspaceId(),
                workspace.getWorkspace().getName(),
                workspace.getWorkspaceRole()
        );
    }

    public List<WorkspaceDto> getUserWorkspacesWhereRoleMember(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found"));

        return userWorkspaceRepository.findMemberWorkspacesByUserId(user.getId())
                .stream().map(this::mapToDto).toList();
    }

    public List<WorkspaceDto> getUserWorkspacesWhereRoleOwner(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found"));

        return userWorkspaceRepository.findOwnerWorkspacesByUserId(user.getId())
                .stream().map(this::mapToDto).toList();
    }

    public List<WorkspaceUserDto> getWorkspaceMembers(Long id) {
        return userWorkspaceRepository.findMembersByWorkspaceId(id)
                .stream()
                .map(user -> new WorkspaceUserDto(user.getEmail()))
                .toList();
    }

    public ResponseEntity<?> deleteWorkspace(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("Користувача не знайдено"));

        // Шукаємо сам простір
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException("Воркспейс не знайдено"));

        // Перевіряємо, чи є юзер власником (завантажуємо список в пам'ять)
        boolean isOwner = user.getWorkspaces().stream().anyMatch(w ->
                Objects.equals(w.getWorkspace().getId(), id) && w.getWorkspaceRole().equals("OWNER")
        );

        if(isOwner) {
            // ФІКС: Розриваємо зв'язок в оперативній пам'яті, щоб Hibernate не "чіплявся" за нього
            user.getWorkspaces().removeIf(w -> Objects.equals(w.getWorkspace().getId(), id));

            // Тепер Hibernate спокійно видалить простір і всі його записи з БД
            workspaceRepository.delete(workspace);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public void shareWorkspace(Long workspaceId, String shareToEmail, String workspaceOwner) {
        User sharedToUser = userRepository.findByEmail(shareToEmail)
                .orElseThrow(() -> new UserNotFound("Користувача для додавання не знайдено"));

        User owner = userRepository.findByEmail(workspaceOwner)
                .orElseThrow(() -> new UserNotFound("Власника не знайдено"));

        // Шукаємо сам простір
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException("Простора не існує"));

        boolean isUserOwner = owner.getWorkspaces().stream().anyMatch(w ->
                Objects.equals(w.getWorkspace().getId(), workspaceId) && w.getWorkspaceRole().equals("OWNER")
        );

        if (!isUserOwner) {
            throw new RuntimeException("У вас немає прав для надання доступу до цього простору");
        }

        // Перевірка, чи вже не є учасником
        boolean alreadyMember = sharedToUser.getWorkspaces().stream().anyMatch(w ->
                Objects.equals(w.getWorkspace().getId(), workspaceId)
        );

        if (!alreadyMember) {
            userWorkspaceRepository.save(
                    UserWorkspace.builder()
                            .user(sharedToUser)
                            .workspace(workspace)
                            .workspaceRole("MEMBER")
                            .build()
            );
        }
    }

    public void createNewWorkspace(String workspaceName, String userEmail){
        // 1. Додатковий захист від null (навіть якщо контролер пропустить)
        if (workspaceName == null || workspaceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва простору не може бути порожньою");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound("Користувача не знайдено"));

        boolean isWorkspaceExists = user.getWorkspaces().stream()
                .anyMatch(w -> Objects.equals((workspaceName.trim()), w.getWorkspace().getName()));

        if(!isWorkspaceExists){

            // 2. СПОЧАТКУ створюємо і зберігаємо сам простір
            Workspace newWorkspace = workspaceRepository.save(
                    Workspace.builder()
                            .name(workspaceName.trim())
                            .build()
            );

            // 3. ПОТІМ створюємо зв'язок між користувачем і збереженим простором
            userWorkspaceRepository.save(
                    UserWorkspace.builder()
                            .user(user)
                            .workspace(newWorkspace)
                            .workspaceRole("OWNER") // ВАЖЛИВО: Робимо творця власником!
                            .build()
            );
        }
    }
}