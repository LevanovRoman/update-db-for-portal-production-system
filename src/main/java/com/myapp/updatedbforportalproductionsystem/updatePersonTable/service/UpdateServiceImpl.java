package com.myapp.updatedbforportalproductionsystem.updatePersonTable.service;

import com.myapp.updatedbforportalproductionsystem.updatePersonTable.entity.Person;
import com.myapp.updatedbforportalproductionsystem.updatePersonTable.repository.PersonRepository;
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

    private final PersonRepository personRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

    @Value("${POSTGRES_EXTERNAL_URL}")
    private String POSTGRES_URL;
    @Value("${POSTGRES_EXTERNAL_USERNAME}")
    private String POSTGRES_USERNAME;
    @Value("${POSTGRES_EXTERNAL_PASSWORD}")
    private String POSTGRES_PASSWORD;

    public UpdateServiceImpl(PersonRepository personRepository, JdbcTemplate jdbcTemplate) {
        this.personRepository = personRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Scheduled(cron = "0 30 11 * * *", zone = "Europe/Moscow")
    public void updateTablePerson() {
        List<Person> personList = new ArrayList<>();
        String querySelectData = "SELECT tab_n, full_name_io, appoint_name, dept_root_name FROM persons_cand" +
                " WHERE persons_cand.d_out > CURRENT_DATE";

        logger.info("Start of the table 'person' update  {}", getCurrentTime());
        // Заполняем таблицу person из persons_cand
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL, POSTGRES_USERNAME, POSTGRES_PASSWORD);
             Statement statement = connection.createStatement()){
            try (ResultSet resultSet = statement.executeQuery(querySelectData)) {
                while (resultSet.next()) {
                    Person person = new Person();
                    person.setTabNumber(resultSet.getString("tab_n"));
                    person.setFullName(resultSet.getString("full_name_io"));
                    person.setAppointName(resultSet.getString("appoint_name"));
                    person.setDepartment(resultSet.getString("dept_root_name"));
                    personList.add(person);
            }

        }
            // Удаляем таблицу person
            truncateTable();
            logger.info("Table 'person' deleted successfully.");
        }catch (Exception e) {
            logger.error("An error occurred", e);
        }
        savePerson(personList);
        logger.info("Table 'person' updated successfully.");
    }

    private void savePerson(List<Person> personList){
        personRepository.saveAll(personList);
    }

    private void truncateTable() {
        jdbcTemplate.execute("TRUNCATE TABLE person RESTART IDENTITY CASCADE");
    }

    private String getCurrentTime() {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }
}
