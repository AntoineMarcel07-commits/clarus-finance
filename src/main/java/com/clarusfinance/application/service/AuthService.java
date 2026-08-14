package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.application.port.PasswordHasher;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.domain.repository.UserRepository;
import java.util.Objects;

public final class AuthService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordHasher = Objects.requireNonNull(passwordHasher);
    }

    public UserAccount authenticate(String username, char[] password) {
        String normalizedUsername = username == null ? "" : username.trim().toLowerCase();
        if (normalizedUsername.isBlank() || password == null || password.length == 0) {
            throw new ValidationException("Escribe el usuario y la contraseña");
        }
        UserAccount user = userRepository.findByUsername(normalizedUsername)
                .filter(UserAccount::active)
                .orElseThrow(() -> new ValidationException("Usuario o contraseña incorrectos"));
        String rawPassword = new String(password);
        if (!passwordHasher.matches(rawPassword, user.passwordHash())) {
            throw new ValidationException("Usuario o contraseña incorrectos");
        }
        return user;
    }
}
