package com.clarusfinance.domain.repository;

import com.clarusfinance.domain.model.UserAccount;
import java.util.Optional;

public interface UserRepository {
    Optional<UserAccount> findByUsername(String username);
}
