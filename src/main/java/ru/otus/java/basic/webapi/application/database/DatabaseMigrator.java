package ru.otus.java.basic.webapi.application.database;

import org.flywaydb.core.Flyway;
import ru.otus.java.basic.webapi.application.Config;

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
