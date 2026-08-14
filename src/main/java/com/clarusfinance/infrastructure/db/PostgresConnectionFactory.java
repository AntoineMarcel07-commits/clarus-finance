package com.clarusfinance.infrastructure.db;

import com.clarusfinance.infrastructure.config.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class PostgresConnectionFactory implements ConnectionFactory {
    private final AppConfig config;

    public PostgresConnectionFactory(AppConfig config) {
        this.config = Objects.requireNonNull(config);
    }

    @Override
    public Connection open() throws SQLException {
        return DriverManager.getConnection(
                config.databaseUrl(),
                config.databaseUser(),
                config.databasePassword());
    }
}
