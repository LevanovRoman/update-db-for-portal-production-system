package com.myapp.updatedbforportalproductionsystem.updatePersonalTable.service;

import com.myapp.updatedbforportalproductionsystem.updatePersonalTable.entity.Personal;
import com.myapp.updatedbforportalproductionsystem.updatePersonalTable.repository.PersonalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService{

    private final PersonalRepository personalRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

    @Value("${POSTGRES_EXTERNAL_URL}")
    private String POSTGRES_URL;
    @Value("${POSTGRES_EXTERNAL_USERNAME}")
    private String POSTGRES_USERNAME;
    @Value("${POSTGRES_EXTERNAL_PASSWORD}")
    private String POSTGRES_PASSWORD;

    public UpdateServiceImpl(PersonalRepository personalRepository, JdbcTemplate jdbcTemplate) {
        this.personalRepository = personalRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Moscow")
    public void updateTablePersonal() {
        List<Personal> personalList = new ArrayList<>();
        String querySelectData = "SELECT tab_n, full_name_io, appoint_name FROM persons_cand WHERE card_id < 50";

        logger.info("Начало выполнения обновления таблицы personal {}", getCurrentTime());
        // Заполняем таблицу personal из persons_cand
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL, POSTGRES_USERNAME, POSTGRES_PASSWORD);
             Statement statement = connection.createStatement()){
            try (ResultSet resultSet = statement.executeQuery(querySelectData)) {
                while (resultSet.next()) {
                    Personal personal = new Personal();
                    personal.setTabNumber(resultSet.getString("tab_n"));
                    personal.setFullName(resultSet.getString("full_name_io"));
                    personal.setAppointName(resultSet.getString("appoint_name"));
                    personalList.add(personal);
            }

        }
            // Удаляем таблицу personal
            truncateTable();
            logger.info("Таблица personal успешно удалена.");
        }catch (Exception e) {
            logger.error("An error occurred", e);
        }
        savePersonal(personalList);
        logger.info("Таблица personal успешно обновлена.");
    }

    private void savePersonal(List<Personal> personalList){
        personalRepository.saveAll(personalList);
    }

    private void truncateTable() {
        jdbcTemplate.execute("TRUNCATE TABLE personal RESTART IDENTITY CASCADE");
    }

    private String getCurrentTime() {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }
}
