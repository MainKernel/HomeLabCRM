package com.homelab.suit.security.controller;

import com.homelab.suit.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/admin/user/new")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@RequestBody Map<String, String> payload){
        userService.saveNewUser(payload.get("email"), payload.get("password"));
        return ResponseEntity.ok().build();
    }
}
