package ru.skypro.zveropolis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@Lazy
@DependsOn("telegramBotInit")
public class TelegramBotSendMessage {
    private final TelegramBotInit telegramBotInit;

    public Message sendMessage(SendMessage sendMessage) {
        try {
            return telegramBotInit.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
