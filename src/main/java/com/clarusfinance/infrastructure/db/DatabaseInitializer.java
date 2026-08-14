package com.clarusfinance.infrastructure.db;

import com.clarusfinance.application.exception.DataAccessException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public final class DatabaseInitializer {
    private final ConnectionFactory connectionFactory;

    public DatabaseInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    public void initialize() {
        try (Connection connection = connectionFactory.open()) {
            connection.setAutoCommit(false);
            executeScript(connection, "/db/schema.sql");
            executeScript(connection, "/db/seed.sql");
            connection.commit();
        } catch (SQLException | IOException exception) {
            throw new DataAccessException("No se pudo inicializar la base de datos", exception);
        }
    }

    private void executeScript(Connection connection, String resource) throws IOException, SQLException {
        try (InputStream input = DatabaseInitializer.class.getResourceAsStream(resource)) {
            if (input == null) {
                throw new IOException("No se encontró el recurso " + resource);
            }
            String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("(?m)^\\s*--.*$", "");
            for (String statementText : sql.split(";")) {
                if (!statementText.isBlank()) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(statementText.trim());
                    }
                }
            }
        }
    }
}
