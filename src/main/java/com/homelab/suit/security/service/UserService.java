package com.homelab.suit.security.service;

import com.homelab.suit.security.Role;
import com.homelab.suit.security.User;
import com.homelab.suit.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveNewUser(String email, String password){
        userRepository.save(User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build());
    }
}
