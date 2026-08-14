package com.clarusfinance.domain.model;

public record UserAccount(Long id, String username, String displayName, String passwordHash, boolean active) {
}
