package com.myapp.updatedbforportalproductionsystem.updatePersonTable.controller;

import com.myapp.updatedbforportalproductionsystem.updatePersonTable.service.UpdateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("update-person")
public class UpdateController {

    private final UpdateService updateService;

    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }


    @GetMapping
    public String getDataFromDb() throws SQLException {
        updateService.updateTablePerson();
        return "ok";
    }
}
