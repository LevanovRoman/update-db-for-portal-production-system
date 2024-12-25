package com.myapp.updatedbforportalproductionsystem.updatePersonTable.service;

import com.myapp.updatedbforportalproductionsystem.config.UpdateTableConfig;
import com.myapp.updatedbforportalproductionsystem.updatePersonTable.entity.Person;
import com.myapp.updatedbforportalproductionsystem.updatePersonTable.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateServiceImpl implements UpdateService{

    private final PersonRepository personRepository;
    private final UpdateTableConfig updateTableConfig;

    private static final Logger logger = LoggerFactory.getLogger(UpdateServiceImpl.class);

    public UpdateServiceImpl(PersonRepository personRepository, UpdateTableConfig updateTableConfig) {
        this.personRepository = personRepository;
        this.updateTableConfig = updateTableConfig;
    }

    @Override
    @Scheduled(cron = "0 15 08 * * *", zone = "Europe/Moscow")
    public void updateTablePerson() {
        List<Person> personList = new ArrayList<>();
        String querySelectData = "SELECT tab_n, INITCAP(\"full_name_io\") AS full_name_io, \"appoint_name\", dept_root_name " +
                "FROM persons_cand WHERE persons_cand.d_out > CURRENT_DATE";

        logger.info("Start of the table 'person' update  {}", updateTableConfig.getCurrentTime());
        // Заполняем таблицу person из 'persons_cand'
        try (Connection connection = updateTableConfig.getConnectionPostgres();
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
            updateTableConfig.truncateTable("person");
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
}
