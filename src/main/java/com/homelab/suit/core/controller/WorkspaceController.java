package com.homelab.suit.core.controller;

import com.homelab.suit.core.dto.WorkspaceDto;
import com.homelab.suit.core.services.UserWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class WorkspaceController {
    private final UserWorkspaceService workspaceService;

    @GetMapping("/workspaces")
    public List<WorkspaceDto> getUserWorkspaces(Principal principal){
        return workspaceService.getUserWorkspaces(principal.getName());
    }
}
