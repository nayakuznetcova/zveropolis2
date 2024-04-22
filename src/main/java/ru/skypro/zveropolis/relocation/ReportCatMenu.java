package ru.skypro.zveropolis.relocation;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.repository.SubscriberRepository;

@Service
public class ReportCatMenu implements State{
    private final SubscriberRepository subscriberRepository;
    private final Relocation relocation;
    private final TelegramBotSendMessage telegramBotSendMessage;

    private final String BACK_CAT_REPORT = "BACK_CAT_REPORT";
    private final String SEND_REPORT = "SEND_REPORT";

    public ReportCatMenu(SubscriberRepository subscriberRepository, Relocation relocation,@Lazy TelegramBotSendMessage telegramBotSendMessage) {
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
        this.telegramBotSendMessage = telegramBotSendMessage;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        } else if (update.getMessage().hasText() && update.getMessage().hasPhoto()) {

        } else if (update.getMessage().hasText()) {

        } else if (update.getMessage().hasPhoto()) {

        }
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
}
