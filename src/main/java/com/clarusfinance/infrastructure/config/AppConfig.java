package com.clarusfinance.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record AppConfig(String databaseUrl, String databaseUser, String databasePassword) {

    public static AppConfig load() {
        Properties properties = new Properties();
        Path configPath = Path.of(System.getenv().getOrDefault(
                "CLARUS_CONFIG", "config/application.properties"));
        if (Files.isRegularFile(configPath)) {
            try (InputStream input = Files.newInputStream(configPath)) {
                properties.load(input);
            } catch (IOException exception) {
                throw new IllegalStateException("No se pudo leer " + configPath, exception);
            }
        }
        return new AppConfig(
                value("CLARUS_DB_URL", properties, "db.url", "jdbc:postgresql://localhost:5432/clarus_finance"),
                value("CLARUS_DB_USER", properties, "db.user", "postgres"),
                value("CLARUS_DB_PASSWORD", properties, "db.password", "postgres"));
    }

    private static String value(String environmentName, Properties properties,
            String propertyName, String defaultValue) {
        String environmentValue = System.getenv(environmentName);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }
        return properties.getProperty(propertyName, defaultValue).trim();
    }
}
