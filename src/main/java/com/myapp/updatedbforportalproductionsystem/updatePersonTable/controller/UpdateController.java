package com.myapp.updatedbforportalproductionsystem.updatePersonTable.controller;

import com.myapp.updatedbforportalproductionsystem.updatePersonTable.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("getdata")
public class UpdateController {

    private final UpdateService updateService;

    @Autowired
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @GetMapping
    public String getDataFromDb() throws SQLException {
        updateService.updateTablePerson();
        return "ok";
    }
}