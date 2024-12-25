package com.myapp.updatedbforportalproductionsystem.config;

import com.myapp.updatedbforportalproductionsystem.updatePersonTable.service.UpdateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class UpdateTableConfig {

    private final JdbcTemplate jdbcTemplate;

    public UpdateTableConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(UpdateTableConfig.class);

    @Value("${POSTGRES_EXTERNAL_URL_FORSSTORE}")
    private String POSTGRES_URL_FORS;
    @Value("${POSTGRES_EXTERNAL_URL}")
    private String POSTGRES_URL;
    @Value("${POSTGRES_EXTERNAL_USERNAME}")
    private String POSTGRES_USERNAME;
    @Value("${POSTGRES_EXTERNAL_PASSWORD}")
    private String POSTGRES_PASSWORD;

    public String getCurrentTime() {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }

    public void truncateTable(String tableName) {
        jdbcTemplate.execute("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE");
    }

    public Connection getConnectionPostgres() throws SQLException {
        return DriverManager.getConnection(POSTGRES_URL, POSTGRES_USERNAME, POSTGRES_PASSWORD);
    }

    public Connection getConnectionPostgresFors() throws SQLException {
        return DriverManager.getConnection(POSTGRES_URL_FORS, POSTGRES_USERNAME, POSTGRES_PASSWORD);
    }
}
