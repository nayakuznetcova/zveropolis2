package ru.skypro.zveropolis.relocation;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class CallVolunteerMenu implements State{
    private final String CALL_VOLUNTEER = "CALL_VOLUNTEER";

    @Override
    public void execute(Update update) {
        if(update.getMessage().hasText()){

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

            }
        }
    }
}
