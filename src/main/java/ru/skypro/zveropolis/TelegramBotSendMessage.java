package ru.skypro.zveropolis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
@RequiredArgsConstructor
@Lazy
@DependsOn("telegramBotInit")
public class TelegramBotSendMessage {
    private final TelegramBotInit telegramBotInit;
    @Value("${path.file}")
    private String pathFile;

    public Message sendMessage(SendMessage sendMessage) {
        try {
            return telegramBotInit.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public File sendFile(GetFile getFile, String nameFile){
        try {
            org.telegram.telegrambots.meta.api.objects.File file = telegramBotInit.execute(getFile);
            return telegramBotInit.downloadFile(file, new File(pathFile + nameFile + ".png"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
