package com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.service;

import com.myapp.updatedbforportalproductionsystem.config.UpdateTableConfig;
import com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.entity.Suggestion;
import com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.repository.SuggestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService{

    private final SuggestionRepository suggestionRepository;
    private final UpdateTableConfig updateTableConfig;

    private static final Logger logger = LoggerFactory.getLogger(SuggestionServiceImpl.class);

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, UpdateTableConfig updateTableConfig) {
        this.suggestionRepository = suggestionRepository;
        this.updateTableConfig = updateTableConfig;
    }

    @Override
    @Scheduled(cron = "0 25 8 * * *", zone = "Europe/Moscow")
    public void updateTableSuggestion() {
        List<Suggestion> suggestionList = new ArrayList<>();
        String querySelectData = "SELECT title, numberanddate, department, prepperson, regdate, state, isexecuted, field21\n" +
                "FROM fors_store.etl_ppu\n" +
                "WHERE EXTRACT(YEAR FROM TO_DATE(createdate, 'YYYY-MM-DD')) = 2024\n" +
                "ORDER BY TO_DATE(createdate, 'YYYY-MM-DD') ASC;";

        logger.info("Start of the table 'suggestion' update  {}", updateTableConfig.getCurrentTime());
        // Заполняем таблицу person из persons_cand
        try (Connection connection = updateTableConfig.getConnectionPostgresFors();
             Statement statement = connection.createStatement()) {
            Suggestion suggestion = null;
            try (ResultSet resultSet = statement.executeQuery(querySelectData)) {
                while (resultSet.next()) {
                    suggestion = new Suggestion();
                    suggestion.setTitle(resultSet.getString("title"));
                    suggestion.setNumberAndDateRegistration(resultSet.getString("numberanddate"));
                    suggestion.setDepartment(resultSet.getString("department"));
                    suggestion.setAuthor(resultSet.getString("prepperson"));
                    suggestion.setRegistered(resultSet.getString("regdate"));
                    suggestion.setAgreed(resultSet.getString("state"));
                    suggestion.setImplemented(resultSet.getString("isexecuted"));
                    suggestion.setDateImplementation(resultSet.getString("field21"));
                    suggestionList.add(suggestion);
                }
            }
            // Удаляем таблицу 'suggestion'
            updateTableConfig.truncateTable("suggestion");
            logger.info("Table 'suggestion' deleted successfully.");
        }catch (Exception e) {
            logger.error("An error occurred", e);
        }
        suggestionRepository.saveAll(suggestionList);
        logger.info("Table 'suggestion' updated successfully.");
    }
}
