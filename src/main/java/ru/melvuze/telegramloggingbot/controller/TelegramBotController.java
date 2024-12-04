package ru.melvuze.telegramloggingbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.melvuze.telegramloggingbot.service.AuthService;
import ru.melvuze.telegramloggingbot.service.LoggingService;

@Component
@RequiredArgsConstructor
public class TelegramBotController implements LongPollingSingleThreadUpdateConsumer {

    private final LoggingService loggingService;
    private final AuthService authService;

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            if (authService.isAuthorized(update.getMessage().getChatId())) {
                loggingService.sendMessage(update.getMessage().getChatId().toString(), "Hello, you are already authorized");
            } else {

                if (update.getMessage().getText().equals("/start")) {
                    authService.sendCode(update.getMessage().getChatId());
                } else {
                    authService.register(update.getMessage().getChatId(), update.getMessage().getText());
                }

            }

        }
    }

}
