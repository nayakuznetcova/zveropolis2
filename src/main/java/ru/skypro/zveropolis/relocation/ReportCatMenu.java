package ru.skypro.zveropolis.relocation;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.model.*;
import ru.skypro.zveropolis.repository.PetRepository;
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
    private final PetRepository petRepository;

    private final String BACK_CAT_REPORT = "BACK_CAT_REPORT";
    private final String SEND_REPORT = "SEND_REPORT";

    public ReportCatMenu(SubscriberRepository subscriberRepository, @Lazy Relocation relocation, @Lazy TelegramBotSendMessage telegramBotSendMessage, ReportRepository reportRepository, PhotoRepository photoRepository, PetRepository petRepository) {
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
        this.telegramBotSendMessage = telegramBotSendMessage;
        this.reportRepository = reportRepository;
        this.photoRepository = photoRepository;
        this.petRepository = petRepository;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        } else if (update.getMessage().hasPhoto()) {
            textAndPhoto(update);
        } else if (update.getMessage().hasText()) {

        }
    }

    private void textAndPhoto(Update update){
        Long chatId = update.getMessage().getChatId();
        List<PhotoSize> photoSizes = update.getMessage().getPhoto();
        Photo photo = new Photo();
        PhotoSize photoSize = photoSizes.get(photoSizes.size() - 1);
        GetFile getFile = new GetFile(photoSize.getFileId());
        File file = telegramBotSendMessage.sendFile(getFile, UUID.randomUUID().toString());
        photo.setPath(file.getPath());
        if(update.getMessage().getCaption() == null){
            SendMessage sendMessageNotKeyboard = createSendMessageNotKeyboard("добавьте подпись", chatId);
            telegramBotSendMessage.sendMessage(sendMessageNotKeyboard);
        } else {
            String caption = update.getMessage().getCaption();
            Report report = new Report();
            Pet pet = petRepository.findByUsersChatId(chatId);
            report.setDescription(caption);
            report.setPet(pet);
            Report saveReport = reportRepository.save(report);
            photo.setReport(saveReport);
            SendMessage sendMessageNotKeyboard = createSendMessageNotKeyboard("отчет сохранен", chatId);
            telegramBotSendMessage.sendMessage(sendMessageNotKeyboard);

            subscriberRepository.putStateBot(chatId, StateBot.START_MENU);
            State state = relocation.getState(chatId);
            state.execute(update);

        }
        photoRepository.save(photo);
    }

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

    private SendMessage createSendMessageNotKeyboard(String text, Long chatId){
        SendMessage createSendMessage = new SendMessage();
        createSendMessage.setText(text);
        createSendMessage.setChatId(chatId);
        return createSendMessage;
    }

    public void handleDailyReportForm(Update update) {
        if (update.getMessage().hasPhoto() && !update.getMessage().hasText()) {
            requestText(update);
        } else if (update.getMessage().hasText() && !update.getMessage().hasPhoto()) {
            requestPhoto(update);
        } else {
            sendIncompleteReportWarning(update);
        }
    }

    private void requestText(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessageNotKeyboard("Пожалуйста, пришлите текстовое описание", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    private void requestPhoto(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessageNotKeyboard("Пожалуйста, пришлите фотографию", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    private void sendIncompleteReportWarning(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessageNotKeyboard("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void congratulateAdopter(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("Поздравляем! Вы успешно прошли испытательный срок!", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void notifyAdditionalTrialPeriod(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("Вам назначено дополнительное время испытательного срока на 14 дней.", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void notifyUnsuccessfulTrialPeriod(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("К сожалению, вы не прошли испытательный срок. Следуйте инструкциям для дальнейших шагов.", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void callVolunteer(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("Оставьте свой номер телефона, волонтер свяжется с вами.", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void warning2day(Update update){
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("Дорогой усыновитель! пришлите отчет о состоянии животного. Напоминаем: отчет отправляется ежедневно!", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void warning2dayVolonteer(Update update){
        Long chatId = update.getMessage().getChatId();
        if (!update.getMessage().hasText()) new Users(createSendMessage("Дорогой волонтёр! Усыновитель не присылает отчет о состоянии животного более 2 дней. Обратитесь к усыновителю за обратной связью!", chatId));
    }
}