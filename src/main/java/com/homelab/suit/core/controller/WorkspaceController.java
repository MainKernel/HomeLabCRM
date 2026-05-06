package com.homelab.suit.core.controller;

import com.homelab.suit.core.dto.WorkspaceDto;
import com.homelab.suit.core.dto.WorkspaceUserDto;
import com.homelab.suit.core.services.UserWorkspaceService;
import com.homelab.suit.inventory.model.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkspaceController {
    private final UserWorkspaceService workspaceService;

    @GetMapping("/workspaces")
    public List<WorkspaceDto> getUserWorkspaces(Principal principal){
        return workspaceService.getUserWorkspaces(principal.getName());
    }
    @GetMapping("/workspaces/owner")
    public List<WorkspaceDto> getOwnerWorkspaces(Principal principal){
        return workspaceService.getUserWorkspacesWhereRoleOwner(principal.getName());
    }
    @GetMapping("/workspaces/member")
    public List<WorkspaceDto> getMemberWorkspaces(Principal principal){
        return workspaceService.getUserWorkspacesWhereRoleMember(principal.getName());
    }
    @GetMapping("/workspace/members")
    public List<WorkspaceUserDto> getWorkspaceMembers(@RequestHeader("X-Workspace-Id") Long workspaceId){
        return workspaceService.getWorkspaceMembers(workspaceId);
    }
    @DeleteMapping("/workspaces/{id}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable Long id, Principal principal){
        return workspaceService.deleteWorkspace(id, principal.getName());
    }

    @PostMapping("/workspaces/share/{id}")
    public ResponseEntity<?> shareWorkspace(@PathVariable Long id, @RequestBody Map<String, String> payload,
                                            Principal principal) {
        String sharingTo = payload.get("email");

        workspaceService.shareWorkspace(id, sharingTo, principal.getName());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/workspaces/new")
    public ResponseEntity<?> newWorkspace(@RequestBody Map<String, String> payload, Principal principal){
        // Дістаємо значення по ключу "name" з JSON тіла
        String name = payload.get("name");

        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }

        workspaceService.createNewWorkspace(name, principal.getName());
        return ResponseEntity.ok().build();
    }
}
