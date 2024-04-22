package ru.skypro.zveropolis.relocation;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
@Service
public class DogMenu implements State{
    @Override
    public void execute(Update update) {

    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup() {
        return null;
    }
}
