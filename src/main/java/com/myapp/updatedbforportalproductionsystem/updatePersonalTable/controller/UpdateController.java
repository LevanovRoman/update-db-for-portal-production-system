package com.myapp.updatedbforportalproductionsystem.updatePersonalTable.controller;

import com.myapp.updatedbforportalproductionsystem.updatePersonalTable.service.UpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("getdata")
//@RequiredArgsConstructor
public class UpdateController {

    private final UpdateService updateService;

    @Autowired
    public UpdateController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @GetMapping
    public String getDataFromDb() throws SQLException {
        updateService.updateTablePersonal();
        return "ok";
    }
}
