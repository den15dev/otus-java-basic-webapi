package ru.otus.java.basic.webapi.application;

import org.flywaydb.core.Flyway;

public class DatabaseMigrator {
    private final Config config;

    public DatabaseMigrator(Config config) {
        this.config = config;
    }

    public void migrate() {
        Flyway.configure()
                .dataSource(
                        config.get("db.url"),
                        config.get("db.username"),
                        config.get("db.password")
                )
                .load()
                .migrate();
    }
}
