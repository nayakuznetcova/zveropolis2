package ru.skypro.zveropolis.relocation;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.model.Photo;
import ru.skypro.zveropolis.model.Report;
import ru.skypro.zveropolis.repository.PhotoRepository;
import ru.skypro.zveropolis.repository.ReportRepository;
import ru.skypro.zveropolis.repository.SubscriberRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ReportCatMenu implements State{
    private final SubscriberRepository subscriberRepository;
    private final Relocation relocation;
    private final TelegramBotSendMessage telegramBotSendMessage;
    private final ReportRepository reportRepository;
    private final PhotoRepository photoRepository;

    private final String BACK_CAT_REPORT = "BACK_CAT_REPORT";
    private final String SEND_REPORT = "SEND_REPORT";

    public ReportCatMenu(SubscriberRepository subscriberRepository, @Lazy Relocation relocation, @Lazy TelegramBotSendMessage telegramBotSendMessage, ReportRepository reportRepository, PhotoRepository photoRepository) {
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
        this.telegramBotSendMessage = telegramBotSendMessage;
        this.reportRepository = reportRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        } else if (update.getMessage().hasText() && update.getMessage().hasPhoto()) {
            textAndPhoto(update);
        } else if (update.getMessage().hasText()) {

        } else if (update.getMessage().hasPhoto()) {

        }
    }
    private void textAndPhoto(Update update){
        String text = update.getMessage().getText();
        List<PhotoSize> photoSizes = update.getMessage().getPhoto();
        Report report = new Report();
        report.setDescription(text);
        Report saveText = reportRepository.save(report);


        Photo photo = new Photo();
        PhotoSize photoSize = photoSizes.get(photoSizes.size() - 1);
        GetFile getFile = new GetFile(photoSize.getFileId());
        File file = telegramBotSendMessage.sendFile(getFile, UUID.randomUUID().toString());
        photo.setPath(file.getPath());
        photo.setReport(saveText);
        photoRepository.save(photo);
    }
    //todo Доделать

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup() {
        return null;
    }

    public void sendMessageAtCallback(Update update){
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        switch (data){
            case BACK_CAT_REPORT -> {
                subscriberRepository.putStateBot(chatId, StateBot.CAT_MENU);
                State state = relocation.getState(chatId);
                state.execute(update);
            }
            case SEND_REPORT -> {
                telegramBotSendMessage.sendMessage(createSendMessage("Вы в репорте", chatId));
            }
        }
    }
    private SendMessage createSendMessage(String text, Long chatId){
        SendMessage createSendMessage = new SendMessage();
        createSendMessage.setText(text);
        createSendMessage.setChatId(chatId);
        createSendMessage.setReplyMarkup(createInlineKeyboardMarkup());
        return createSendMessage;
    }
}
