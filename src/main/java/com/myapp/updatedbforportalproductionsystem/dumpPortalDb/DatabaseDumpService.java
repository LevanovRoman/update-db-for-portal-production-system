package com.myapp.updatedbforportalproductionsystem.dumpPortalDb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DatabaseDumpService {

    @Value("${SERVER_DEV}")
    private String dbHost;

    @Value("${DB_PORT}")
    private String dbPort;

    @Value("${DB_NAME}")
    private String dbName;

    @Value("${POSTGRES_USERNAME}")
    private String dbUser;

    @Value("${POSTGRES_PASSWORD}")
    private String dbPassword;

    @Value("${PROJECT_BACKUP}")
    private String backupDir;

    private final FileCleanupService fileCleanupService;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseDumpService.class);

    public DatabaseDumpService(FileCleanupService fileCleanupService) {
        this.fileCleanupService = fileCleanupService;
    }

    @Scheduled(cron = "0 20 9 * * *", zone = "Europe/Moscow")
    public void createDatabaseDump() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dumpFileName = backupDir + "backup_" + timestamp + ".sql";

        String[] command = {
                "pg_dump", // полный путь к pg_dump
                "-h", dbHost,
                "-p", dbPort,
                "-U", dbUser,
                "-d", dbName,
                "-F", "p",
                "-b",
                "-v",
                "-f", dumpFileName
        };

        // Устанавливаем пароль в переменную окружения
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.environment().put("PGPASSWORD", dbPassword);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("The database dump was successfully created: {}", dumpFileName);
                logger.info("Starting the deletion of the oldest file.");
                fileCleanupService.deleteOldestFile();
            } else {
                logger.error("Error when creating a database dump");
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Error when creating a database dump: ", e);
        }
    }
}
