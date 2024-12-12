package com.myapp.updatedbforportalproductionsystem.statisticsPPU;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${1S_EXTERNAL_URL}")
    private String apiUrl;

    @Value("${1S_EXTERNAL_USERNAME}")
    private String apiUsername;

    @Value("${1S_EXTERNAL_PASSWORD}")
    private String apiPassword;

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    public List<MyDataDto> getDataFromExternalApi() {
        logger.info("Sending request to {}", apiUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiUsername, apiPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<MyDataDto>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<MyDataDto>>() {});
        logger.info("Received response: {}", "OK");
        return response.getBody();
    }

    @Scheduled(cron = "0 35 9 * * *", zone = "Europe/Moscow")
    public void getDataFrom1S(){
        logger.info("Getting data from 1S:  time: {}", getCurrentTime());
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
