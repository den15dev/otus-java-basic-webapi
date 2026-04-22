package ru.otus.java.basic.webapi.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.otus.java.basic.webapi.core.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private final HikariDataSource ds;


    public DataSource(Config config) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.get("db.url"));
        hikariConfig.setUsername(config.get("db.username"));
        hikariConfig.setPassword(config.get("db.password"));

        int maxPoolSize = Integer.parseInt(config.get("db.max-pool-size"));
        hikariConfig.setMaximumPoolSize(maxPoolSize);

        this.ds = new HikariDataSource(hikariConfig);
    }


    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
