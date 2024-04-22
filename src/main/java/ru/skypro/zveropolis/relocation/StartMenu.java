package ru.skypro.zveropolis.relocation;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.repository.SubscriberRepository;

import java.util.ArrayList;
import java.util.List;

@Service

public class StartMenu implements State {
    private final TelegramBotSendMessage telegramBotSendMessage;
    private final SubscriberRepository subscriberRepository;
    private final Relocation relocation;
    private final String CAT_SHELTER = "CAT_SHELTER";
    private final String DOG_SHELTER = "DOG_SHELTER";
    private final String BACK = "BACK";

    public StartMenu(@Lazy TelegramBotSendMessage telegramBotSendMessage, SubscriberRepository subscriberRepository,@Lazy Relocation relocation) {
        this.telegramBotSendMessage = telegramBotSendMessage;
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
    }

    @Override
    public void execute(Update update) {
        if(update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        } else if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals("/start")) {
            sendMessageAtText(update);
        }
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> button = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();

        InlineKeyboardButton catShelter = new InlineKeyboardButton("Приют для кошек");
        catShelter.setCallbackData(CAT_SHELTER);
        buttonRow1.add(catShelter);

        InlineKeyboardButton dogShelter = new InlineKeyboardButton("Приют для собак");
        dogShelter.setCallbackData(DOG_SHELTER);
        buttonRow2.add(dogShelter);

        button.add(buttonRow1);
        button.add(buttonRow2);

        inlineKeyboardMarkup.setKeyboard(button);
        return inlineKeyboardMarkup;
    }
    private SendMessage createSendMessage(String text, Long chatId){
        SendMessage createSendMessage = new SendMessage();
        createSendMessage.setText(text);
        createSendMessage.setChatId(chatId);
        createSendMessage.setReplyMarkup(createInlineKeyboardMarkup());
        return createSendMessage;
    }

    public Message sendMessageAtText(Update update){
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Вас приветсвует приют Зверополис");
        sendMessage.setReplyMarkup(createInlineKeyboardMarkup());
        return telegramBotSendMessage.sendMessage(sendMessage);
    }

    public void sendMessageAtCallback(Update update){
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        switch (data){
            case CAT_SHELTER -> {
                subscriberRepository.putStateBot(chatId, StateBot.CAT_MENU);
                State state = relocation.getState(chatId);
                state.execute(update);
            }
            case DOG_SHELTER -> {
                subscriberRepository.putStateBot(chatId, StateBot.DOG_MENU);
                State state = relocation.getState(chatId);
                state.execute(update);
            }
            case BACK -> {
                telegramBotSendMessage.sendMessage(createSendMessage("Вы вернулись в предыдущее меню", chatId));
            }
            default -> throw new IllegalStateException("Unexpected value: " + data);
        }
    }
}
