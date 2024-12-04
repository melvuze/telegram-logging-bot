package ru.melvuze.telegramloggingbot.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.melvuze.telegramloggingbot.util.TelegramUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Setter
public class LoggingService {

    private final TelegramUtils telegramUtils;

    private String chatId;

    public void sendMessage(String chatId, String text) {
        telegramUtils.sendMessage(chatId, text);
    }

    public void sendSymbolsStartsWithPointer(File log, long pointer) {
        byte[] newBytesFromLog = new byte[(int) (log.length() - pointer)];

        try (FileInputStream inputStream = new FileInputStream(log)) {
            byte[] logBytes = inputStream.readAllBytes();
            for (int i = 0; i < newBytesFromLog.length; i++) {
                newBytesFromLog[i] = logBytes[i + (int) (pointer)];
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String[] newLinesFromLog = new String(newBytesFromLog).split("\\r?\\n");

        Arrays.stream(newLinesFromLog).filter((line) -> !line.isEmpty()).forEach((line) -> sendMessage(chatId, line));
    }

}
