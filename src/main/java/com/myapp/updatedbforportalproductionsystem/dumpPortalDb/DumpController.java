package com.myapp.updatedbforportalproductionsystem.dumpPortalDb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dump-db")
public class DumpController {

    private final DatabaseDumpService databaseDumpService;

    public DumpController(DatabaseDumpService databaseDumpService) {
        this.databaseDumpService = databaseDumpService;
    }

    @GetMapping
    public String dumpDb(){
        databaseDumpService.createDatabaseDump();
        return "DONE";
    }
}
