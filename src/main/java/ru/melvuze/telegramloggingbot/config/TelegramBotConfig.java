package ru.melvuze.telegramloggingbot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.melvuze.telegramloggingbot.controller.TelegramBotController;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {

    @Value("${telegram.token}")
    private String token;

    private final TelegramBotController telegramBotController;

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() throws TelegramApiException {
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(token, telegramBotController);
        return botsApplication;
    }

}
