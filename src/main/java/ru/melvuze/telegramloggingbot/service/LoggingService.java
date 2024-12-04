package ru.melvuze.telegramloggingbot.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.melvuze.telegramloggingbot.domain.repository.AccountRepository;
import ru.melvuze.telegramloggingbot.util.TelegramUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Setter
public class LoggingService {

    private final TelegramUtils telegramUtils;
    private final AccountRepository accountRepository;

    public void sendMessage(String chatId, String text) {
        telegramUtils.sendMessage(chatId, text);
    }

    public void broadcastFileFromPointer(File log, long pointer) {
        byte[] newBytesFromLog = new byte[(int) (log.length() - pointer)];

        try (FileInputStream inputStream = new FileInputStream(log)) {
            byte[] logBytes = inputStream.readAllBytes();
            System.arraycopy(logBytes, (int) (pointer), newBytesFromLog, 0, newBytesFromLog.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String[] newLinesFromLog = new String(newBytesFromLog).split("\\r?\\n");

        Arrays.stream(newLinesFromLog)
                .filter((line) -> !line.isEmpty())
                .forEach((line) -> accountRepository.findAll()
                        .forEach((account) -> sendMessage(String.valueOf(account.getId()), line))
                );
    }

}
