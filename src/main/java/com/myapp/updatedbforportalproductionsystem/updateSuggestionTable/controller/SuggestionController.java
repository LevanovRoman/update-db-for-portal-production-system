package com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.controller;

import com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.service.SuggestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("update-suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public String getSuggestionFromDb() throws SQLException {
        suggestionService.updateTableSuggestion();
        return "ok";
    }
}
