package com.myapp.updatedbforportalproductionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class UpdateDbForPortalProductionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpdateDbForPortalProductionSystemApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 секунд для подключения
        factory.setReadTimeout(5000);    // 5 секунд для чтения ответа
        return new RestTemplate();
    }

}
