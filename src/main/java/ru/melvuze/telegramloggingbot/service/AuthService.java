package ru.melvuze.telegramloggingbot.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.melvuze.telegramloggingbot.domain.model.Account;
import ru.melvuze.telegramloggingbot.domain.repository.AccountRepository;
import ru.melvuze.telegramloggingbot.util.TelegramUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final TelegramUtils telegramUtils;

    private final Random random = new Random();
    private final Map<Long, Integer> codes = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public void sendCode(Long chatId) {
        telegramUtils.sendMessage(String.valueOf(chatId), "Please enter code from SpringBoot application");
        int code = 100000 + random.nextInt(900000);
        codes.put(chatId, code);
        logger.info("Write code {} in chat {}", code, chatId);
    }

    public void register(Long chatId, String code) {
        int codeNumber;
        try {
            codeNumber = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            telegramUtils.sendMessage(String.valueOf(chatId), "Wrong code");
            return;
        }
        if (codes.containsKey(chatId) && codeNumber == codes.get(chatId)) {
            telegramUtils.sendMessage(String.valueOf(chatId), "Code is correct. Now you are authorized");
            accountRepository.save(
                    Account.builder()
                            .id(chatId)
                            .build()
            );
            codes.remove(chatId);
        } else {
            telegramUtils.sendMessage(String.valueOf(chatId), "Wrong code");
        }
    }

    public boolean isAuthorized(Long id) {
        return accountRepository.findById(id).isPresent();
    }

}
