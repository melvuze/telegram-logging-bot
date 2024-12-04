package ru.melvuze.telegramloggingbot.task;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.melvuze.telegramloggingbot.service.LoggingService;

import java.io.*;

@Component
@RequiredArgsConstructor
public class LoggingTask {

    private final LoggingService loggingService;

    @Value("${telegram.file}")
    private String fileName;

    private File log;
    private long pointer;

    @Scheduled(fixedRate = 5000)
    public void sendMessageIfFileChanged() {
        if (log==null) {
            log = new File(fileName);
            pointer = log.length();
        }
        if (log.length()>pointer) {

            loggingService.sendSymbolsStartsWithPointer(log, pointer);

            pointer = log.length();

        }
    }

}
