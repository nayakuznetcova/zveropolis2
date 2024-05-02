package ru.skypro.zveropolis.relocation;

import liquibase.pro.packaged.L;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.SubscriberRepository;
import ru.skypro.zveropolis.repository.UserRepository;

import java.util.List;
import java.util.Random;

@Service
public class CallVolunteerMenu implements State{
    private final UserRepository userRepository;
    private final TelegramBotSendMessage telegramBotSendMessage;
    private final SubscriberRepository subscriberRepository;
    private final Relocation relocation;
    private final String CALL_VOLUNTEER = "CALL_VOLUNTEER";

    public CallVolunteerMenu(UserRepository userRepository, @Lazy TelegramBotSendMessage telegramBotSendMessage, SubscriberRepository subscriberRepository,@Lazy Relocation relocation) {
        this.userRepository = userRepository;
        this.telegramBotSendMessage = telegramBotSendMessage;
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
    }

    @Override
    public void execute(Update update) {
        if(update.hasMessage()){
            sendMessageAtText(update);
        }else if (update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        }
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup() {
        return null;
    }

    public void sendMessageAtCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        switch (data){
            case CALL_VOLUNTEER ->{
                checkUserName(update);
            }
        }
    }

    public Message checkUserName(Update update){
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Users user = userRepository.findById(chatId).get();
        String username = user.getUsername();
        String phoneNumber = user.getPhoneNumber();
        if (username != null){
            subscriberRepository.putStateBot(chatId, StateBot.START_MENU);
            State state = relocation.getState(chatId);
            state.execute(update);
            return notificationAtVolunteer("@" +username);
        }else if (phoneNumber != null){
            subscriberRepository.putStateBot(chatId, StateBot.START_MENU);
            State state = relocation.getState(chatId);
            state.execute(update);
            return notificationAtVolunteer(phoneNumber);
        }else {
            SendMessage sendMessageNotKeyboard = createSendMessageNotKeyboard("Введите номер телефона", chatId);
            return telegramBotSendMessage.sendMessage(sendMessageNotKeyboard);
        }

    }

    private SendMessage createSendMessageNotKeyboard(String text, Long chatId){
        SendMessage createSendMessage = new SendMessage();
        createSendMessage.setText(text);
        createSendMessage.setChatId(chatId);
        return createSendMessage;
    }

    private Users sendMessageAtText(Update update){
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Users user = userRepository.findById(chatId).get();
        user.setPhoneNumber(text);
        return userRepository.save(user);
    }
    private Message notificationAtVolunteer(String text){
        List<Users> volunteers = userRepository.getUsersByVolunteerIsTrue();
        Random random = new Random();
        int i = random.nextInt(volunteers.size());
        Users users = volunteers.get(i);
        String sendText = "Свяжитесь с этим пользователем: " + text;
        SendMessage sendMessageNotKeyboard = createSendMessageNotKeyboard(sendText, users.getChatId());
        return telegramBotSendMessage.sendMessage(sendMessageNotKeyboard);
    }
}
