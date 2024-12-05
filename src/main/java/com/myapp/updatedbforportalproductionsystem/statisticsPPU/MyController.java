package com.myapp.updatedbforportalproductionsystem.statisticsPPU;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final ApiService apiService;

    public MyController(ApiService apiService) {
        this.apiService = apiService;
    }

//    @GetMapping("/external-data")
//    public List<MyDataDto> getExternalData() {
//        List<MyDataDto> test = apiService.getDataFromExternalApi();
//        System.out.println(test.getFirst().Zaregistrirovano());
//        return test;
//    }
    @GetMapping("/external-data")
    public String getExternalData() {
        apiService.getDataFrom1S();
        return "OK";
    }
}
