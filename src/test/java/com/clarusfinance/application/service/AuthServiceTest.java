package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.infrastructure.security.Pbkdf2PasswordHasher;
import com.clarusfinance.support.TestRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceTest {
    private AuthService service;

    @BeforeEach
    void setUp() {
        Pbkdf2PasswordHasher hasher = new Pbkdf2PasswordHasher();
        UserAccount active = new UserAccount(1L, "admin", "Admin",
                hasher.hash("correcta"), true);
        UserAccount inactive = new UserAccount(2L, "bloqueado", "Bloqueado",
                hasher.hash("correcta"), false);
        service = new AuthService(new TestRepositories.Users(active, inactive), hasher);
    }

    @Test
    void authenticatesActiveUserAndNormalizesUsername() {
        UserAccount result = service.authenticate(" ADMIN ", "correcta".toCharArray());

        assertEquals(1L, result.id());
    }

    @Test
    void rejectsWrongPassword() {
        assertThrows(ValidationException.class, () ->
                service.authenticate("admin", "incorrecta".toCharArray()));
    }

    @Test
    void rejectsMissingOrInactiveUser() {
        assertThrows(ValidationException.class, () ->
                service.authenticate("nadie", "correcta".toCharArray()));
        assertThrows(ValidationException.class, () ->
                service.authenticate("bloqueado", "correcta".toCharArray()));
    }

    @Test
    void rejectsBlankCredentials() {
        assertThrows(ValidationException.class, () -> service.authenticate("", new char[0]));
    }
}
