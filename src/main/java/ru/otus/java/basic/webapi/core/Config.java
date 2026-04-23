package ru.otus.java.basic.webapi.core;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Config {
    private final Properties properties;


    public Config() {
        this.properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("application.properties not found");
            }
            this.properties.load(inputStream);

            overrideByEnvVariables();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }


    public String get(String key) {
        return this.properties.getProperty(key);
    }


    private void overrideByEnvVariables() {
        String serverPort = System.getenv().get("APP_PORT");
        if (serverPort != null) {
            properties.setProperty("server.port", serverPort);
        }

        String dbHost = System.getenv().get("DB_HOST");
        String dbPort = System.getenv().get("DB_PORT");
        String dbName = System.getenv().get("DB_NAME");
        if (dbHost != null && dbPort != null && dbName != null) {
            String dbUrl = "jdbc:postgresql://%s:%s/%s".formatted(dbHost, dbPort, dbName);
            properties.setProperty("db.url", dbUrl);
        }

        String dbUsername = System.getenv().get("DB_USERNAME");
        if (dbUsername != null) {
            properties.setProperty("db.username", dbUsername);
        }

        String dbPassword = System.getenv().get("DB_PASSWORD");
        if (dbPassword != null) {
            properties.setProperty("db.password", dbPassword);
        }
    }
}
