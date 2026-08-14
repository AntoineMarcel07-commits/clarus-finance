package com.clarusfinance.application.service;

import com.clarusfinance.infrastructure.security.Pbkdf2PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Pbkdf2PasswordHasherTest {

    @Test
    void hashesWithSaltAndVerifiesWithoutPlaintext() {
        Pbkdf2PasswordHasher hasher = new Pbkdf2PasswordHasher();
        String first = hasher.hash("secreto");
        String second = hasher.hash("secreto");

        assertNotEquals(first, second);
        assertTrue(hasher.matches("secreto", first));
        assertFalse(hasher.matches("otro", first));
        assertFalse(first.contains("secreto"));
    }

    @Test
    void rejectsMalformedHash() {
        assertFalse(new Pbkdf2PasswordHasher().matches("secreto", "hash-invalido"));
    }
}
