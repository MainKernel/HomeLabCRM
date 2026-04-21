package com.homelab.suit.security.service;


import com.homelab.suit.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Шукаємо користувача в базі за email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача з email " + email + " не знайдено"));
    }
}