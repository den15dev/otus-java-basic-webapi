package ru.otus.java.basic.webapi;

import ru.otus.java.basic.webapi.core.Config;
import ru.otus.java.basic.webapi.core.database.DatabaseMigrator;
import ru.otus.java.basic.webapi.core.server.HttpServer;

public class App {
    public static void main(String[] args) {
        Config config = new Config();

        DatabaseMigrator migrator = new DatabaseMigrator(config);
        migrator.migrate();

        new HttpServer(config).start();
    }
}
