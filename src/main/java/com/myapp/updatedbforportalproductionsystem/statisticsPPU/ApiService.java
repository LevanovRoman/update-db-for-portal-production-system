package com.myapp.updatedbforportalproductionsystem.statisticsPPU;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

//    private final String url = "http://dev1c/Design1CDO3/hs/online-ppu/getOnline_ppu";

    public List<MyDataDto> getDataFromExternalApi() {
        String apiUrl = "http://dev1c/Design1CDO3/hs/online-ppu/getOnline_ppu";
        logger.info("Sending request to {}", apiUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("erpagent", "123");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<MyDataDto>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<MyDataDto>>() {});
        logger.info("Received response: {}", "OK");
        return response.getBody();
    }

    @Scheduled(cron = "0 10 1 * * *", zone = "Europe/Moscow")
    public void getDataFrom1S(){
        logger.info("Получение данных из 1С {}", getCurrentTime());
        List<MyDataDto> myDataDtoList = getDataFromExternalApi();
        logger.info("Zaregistrirovano:   {}", myDataDtoList.getFirst().Zaregistrirovano());
        logger.info("Soglasovano:   {}", myDataDtoList.getFirst().Soglasovano());
        logger.info("Vnedreno:   {}", myDataDtoList.getFirst().Vnedreno());
    }

    private String getCurrentTime() {
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return currentTime.format(formatter);
    }
}
