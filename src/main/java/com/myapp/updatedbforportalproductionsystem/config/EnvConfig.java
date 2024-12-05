package com.myapp.updatedbforportalproductionsystem.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    private final Dotenv dotenv;

    public EnvConfig() {
        dotenv = Dotenv.configure()
                .filename(".env") // Имя файла
                .load();          // Загрузка данных
    }

    public String getEnv(String key) {
        return dotenv.get(key); // Получение значения переменной
    }
}
