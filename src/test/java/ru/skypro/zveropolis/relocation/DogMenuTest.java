package ru.skypro.zveropolis.relocation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.model.Shelter;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.repository.ShelterRepository;
import ru.skypro.zveropolis.repository.SubscriberRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DogMenuTest {
    @Mock
    private TelegramBotSendMessage telegramBotSendMessage;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private Relocation relocation;

    @InjectMocks
    private DogMenu dogMenu;

    private final String INFORMATION_ABOUT_SHELTER = "INFORMATION_ABOUT_SHELTER";
    private final String HOW_TAKE_PET = "HOW_TAKE_PET";
    private final String SEND_REPORT = "SEND_REPORT";
    private final String CALL_VOLUNTEER = "CALL_VOLUNTEER";
    private final String BACK = "BACK";


    @Test
    void executeCaseCatShelter() {
        Update update = new Update();

        CallbackQuery callbackQuery = new CallbackQuery();
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        message.setChat(chat);
        callbackQuery.setMessage(message);
        callbackQuery.setData("DOG_SHELTER");
        update.setCallbackQuery(callbackQuery);
        Shelter shelter = new Shelter();
        shelter.setGreeting("Вас приветствует приют Зверополис. Пожалуйста выберите интересующее Вас действие:");
        when(shelterRepository.findFirstByTypeOfAnimal(eq(TypeOfAnimal.DOG))).thenReturn(shelter);

        dogMenu.execute(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

        Mockito.verify(telegramBotSendMessage).sendMessage(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals("Вас приветствует приют Зверополис. Пожалуйста выберите интересующее Вас действие:", actual.getText());
        assertEquals("1", actual.getChatId());

    }

    @Test
    void executeCaseInformationAboutShelter() {
        Update update = new Update();

        CallbackQuery callbackQuery = new CallbackQuery();
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        message.setChat(chat);
        callbackQuery.setMessage(message);
        callbackQuery.setData("INFORMATION_ABOUT_SHELTER");
        update.setCallbackQuery(callbackQuery);
        Shelter shelter = new Shelter();
        shelter.setInfo("Тут типо о местоположении");
        when(shelterRepository.findFirstByTypeOfAnimal(eq(TypeOfAnimal.DOG))).thenReturn(shelter);

        dogMenu.execute(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

        Mockito.verify(telegramBotSendMessage).sendMessage(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals("Тут типо о местоположении", actual.getText());
        assertEquals("1", actual.getChatId());

    }

    @Test
    void executeHowToTakePet() {
        Update update = new Update();

        CallbackQuery callbackQuery = new CallbackQuery();
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        message.setChat(chat);
        callbackQuery.setMessage(message);
        callbackQuery.setData("HOW_TAKE_PET");
        update.setCallbackQuery(callbackQuery);
        Shelter shelter = new Shelter();
        shelter.setDatingRules("А здесь про правила как взять животное");
        when(shelterRepository.findFirstByTypeOfAnimal(eq(TypeOfAnimal.DOG))).thenReturn(shelter);

        dogMenu.execute(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

        Mockito.verify(telegramBotSendMessage).sendMessage(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals("А здесь про правила как взять животное", actual.getText());
        assertEquals("1", actual.getChatId());

    }

    @Test
    void createInlineKeyboardMarkupCorrect() {
        InlineKeyboardMarkup inlineKeyboardMarkupExp = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> buttonsExp = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow4 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow5 = new ArrayList<>();

        InlineKeyboardButton informationAboutShelter = new InlineKeyboardButton("Информация о приюте для собак");
        informationAboutShelter.setCallbackData(INFORMATION_ABOUT_SHELTER);
        buttonRow1.add(informationAboutShelter);

        InlineKeyboardButton howTakePet = new InlineKeyboardButton("Как взять животное из приюта");
        howTakePet.setCallbackData(HOW_TAKE_PET);
        buttonRow2.add(howTakePet);

        InlineKeyboardButton sendReport = new InlineKeyboardButton("Прислать отчёт");
        sendReport.setCallbackData(SEND_REPORT);
        buttonRow3.add(sendReport);

        InlineKeyboardButton callVolunteer = new InlineKeyboardButton("Позвать волонтёра");
        callVolunteer.setCallbackData(CALL_VOLUNTEER);
        buttonRow4.add(callVolunteer);

        InlineKeyboardButton back = new InlineKeyboardButton("Назад");
        back.setCallbackData(BACK);
        buttonRow5.add(back);


        buttonsExp.add(buttonRow1);
        buttonsExp.add(buttonRow2);
        buttonsExp.add(buttonRow3);
        buttonsExp.add(buttonRow4);
        buttonsExp.add(buttonRow5);
        inlineKeyboardMarkupExp.setKeyboard(buttonsExp);


        assertEquals(dogMenu.createInlineKeyboardMarkup(), inlineKeyboardMarkupExp);
        assertEquals(buttonRow1.get(0).getCallbackData(), INFORMATION_ABOUT_SHELTER);
        assertEquals(buttonRow2.get(0).getCallbackData(), HOW_TAKE_PET);
        assertEquals(buttonRow3.get(0).getCallbackData(), SEND_REPORT);
        assertEquals(buttonRow4.get(0).getCallbackData(), CALL_VOLUNTEER);
        assertEquals(buttonRow5.get(0).getCallbackData(), BACK);

    }
}