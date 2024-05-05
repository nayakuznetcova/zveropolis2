package ru.skypro.zveropolis.relocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.skypro.zveropolis.repository.SubscriberRepository;

@Service
@RequiredArgsConstructor
public class Relocation {
    private final StartMenu startMenu;
    private final CatMenu catMenu;
    private final DogMenu dogMenu;
    private final ReportCatMenu reportCatMenu;
    private final ReportDogMenu reportDogMenu;
    private final CallVolunteerMenu callVolunteerMenu;
    private final SubscriberRepository subscriberRepository;
    private final InfoCatShelterMenu infoCatShelterMenu;
    private final InfoDogShelterMenu infoDogShelterMenu;


    public void doCrossroads(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            subscriberRepository.checkUser(message);
            State state = getState(chatId);
            state.execute(update);
        } else if (update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            Long chatId = message.getChatId();
            subscriberRepository.checkUser(message);
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
            case REPORT_DOG_MENU -> {
                return reportDogMenu;
            }
            case CALL_VOLUNTEER_MENU -> {
                return callVolunteerMenu;
            }
            case INFO_CAT_SHELTER_MENU -> {
                return infoCatShelterMenu;
            }
            case INFO_DOG_SHELTER_MENU -> {
                return infoDogShelterMenu;
            }
        }
        throw new RuntimeException();
        //todo исправить в случае нового функционала и добавить новый кейс
    }
}
