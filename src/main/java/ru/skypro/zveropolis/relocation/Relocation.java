package ru.skypro.zveropolis.relocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.skypro.zveropolis.repository.SubscriberRepository;

@Service
@RequiredArgsConstructor
public class Relocation {
    private final StartMenu startMenu;
    private final CatMenu catMenu;
    private final DogMenu dogMenu;
    private final ReportCatMenu reportCatMenu;

    private final SubscriberRepository subscriberRepository;


    public void doCrossroads(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            subscriberRepository.checkUser(chatId);
            State state = getState(chatId);
            state.execute(update);
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            subscriberRepository.checkUser(chatId);
            State state = getState(chatId);
            state.execute(update);
        }
    }

    public State getState(Long chatId) {
        StateBot stateBot = subscriberRepository.getStateBot(chatId);
        switch (stateBot) {
            case START_MENU -> {
                return startMenu;
            }
            case CAT_MENU -> {
                return catMenu;
            }
            case DOG_MENU -> {
                return dogMenu;
            }
            case REPORT_CAT_MENU -> {
                return reportCatMenu;
            }
        }
        throw new RuntimeException();
        //todo исправить
    }
}
