package ru.skypro.zveropolis.relocation;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface State {
    void execute(Update update);
    InlineKeyboardMarkup createInlineKeyboardMarkup();
}
