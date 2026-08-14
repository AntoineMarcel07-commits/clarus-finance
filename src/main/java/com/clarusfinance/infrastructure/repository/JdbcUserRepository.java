package com.clarusfinance.infrastructure.repository;

import com.clarusfinance.application.exception.DataAccessException;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.domain.repository.UserRepository;
import com.clarusfinance.infrastructure.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public final class JdbcUserRepository implements UserRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcUserRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        String sql = "SELECT id, username, display_name, password_hash, active FROM app_users WHERE username = ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return Optional.empty();
                }
                return Optional.of(new UserAccount(
                        result.getLong("id"),
                        result.getString("username"),
                        result.getString("display_name"),
                        result.getString("password_hash"),
                        result.getBoolean("active")));
            }
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo consultar el usuario", exception);
        }
    }
}
