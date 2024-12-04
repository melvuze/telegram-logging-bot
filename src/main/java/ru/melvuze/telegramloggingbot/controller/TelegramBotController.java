package ru.melvuze.telegramloggingbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.melvuze.telegramloggingbot.service.LoggingService;

@Component
@RequiredArgsConstructor
public class TelegramBotController implements LongPollingSingleThreadUpdateConsumer {

    private final LoggingService loggingService;

    @Value("${telegram.user}")
    private String user;

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    if (update.getMessage().getFrom().getUserName().equals(user)) {
                        loggingService.setChatId(update.getMessage().getChatId().toString());
                        loggingService.sendMessage(update.getMessage().getChatId().toString(), "Hello, you are allowed to use this bot");
                    } else {
                        loggingService.sendMessage(update.getMessage().getChatId().toString(), "You are not allowed to use this bot");
                    }
                }
            }
        }
    }

}
