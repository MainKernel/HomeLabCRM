package com.homelab.suit.security.components;

import com.homelab.suit.security.Role;
import com.homelab.suit.security.User;
import com.homelab.suit.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default.email}")
    private String adminEmail;

    @Value("${app.admin.default.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            log.info("Обліковий запис адміністратора не знайдено. Створюємо...");

            User admin = User.builder()
                    .email(adminEmail)
                    // ОБОВ'ЯЗКОВО хешуємо пароль! Інакше Spring Security його не прийме
                    .passwordHash(passwordEncoder.encode(adminPassword))
                    .role(Role.ROLE_ADMIN) // Використовуємо ваш Enum
                    // Додайте інші обов'язкові поля, якщо вони є у вашому Entity
                    .build();

            userRepository.save(admin);
            log.info("Адміністратор успішно створений: {}", adminEmail);
        } else {
            log.info("Обліковий запис адміністратора вже існує. Пропускаємо створення.");
        }
    }
}