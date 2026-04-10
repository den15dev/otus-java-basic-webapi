package ru.otus.java.basic.webapi.application;

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

        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }


    public String get(String key) {
        return this.properties.getProperty(key);
    }
}
