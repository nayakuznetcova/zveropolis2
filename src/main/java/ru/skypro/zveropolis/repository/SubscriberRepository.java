package ru.skypro.zveropolis.repository;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.relocation.StateBot;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SubscriberRepository {
    private final UserRepository userRepository;

    private ConcurrentMap<Long, StateBot> stateMap = new ConcurrentHashMap<>();

    public SubscriberRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUser(Message message) {
        Long chatId = message.getChatId();
        if (stateMap.containsKey(chatId)) {
            return true;
        }
        Users users = new Users();
        users.setChatId(chatId);
        users.setFirstName(message.getChat().getFirstName());
        users.setUsername(message.getChat().getUserName());
        userRepository.save(users);
        stateMap.put(chatId, StateBot.START_MENU);
        return false;
    }

    public StateBot getStateBot(Long chatId) {
        return stateMap.get(chatId);
    }

    public StateBot putStateBot(Long chatId, StateBot stateBot) {
        return stateMap.put(chatId, stateBot);
    }
}
