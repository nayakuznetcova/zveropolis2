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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CatMenuTest {
    @Mock
    private TelegramBotSendMessage telegramBotSendMessage;
    @Mock
    private ShelterRepository shelterRepository;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private Relocation relocation;

    @InjectMocks
    private CatMenu catMenu;

    private final String INFORMATION_ABOUT_SHELTER = "INFORMATION_ABOUT_SHELTER";
    private final String HOW_TAKE_PET = "HOW_TAKE_PET";
    private final String SEND_REPORT = "SEND_REPORT";
    private final String CALL_VOLUNTEER = "CALL_VOLUNTEER";
    private final String BACK = "BACK";
    private final String CONTACT_INFORMATION_FOR_ADMISSION = "CONTACT_INFORMATION_FOR_ADMISSION";
    private final String SAFETY_PRECAUTIONS = "SAFETY_PRECAUTIONS";


    @Test
    void executeCaseCatShelter() {
        Update update = new Update();

        CallbackQuery callbackQuery = new CallbackQuery();
        Chat chat = new Chat();
        chat.setId(1L);
        Message message = new Message();
        message.setChat(chat);
        callbackQuery.setMessage(message);
        callbackQuery.setData("CAT_SHELTER");
        update.setCallbackQuery(callbackQuery);
        Shelter shelter = new Shelter();
        shelter.setGreeting("Пожалуйста выберите интересующее вас действие:");
        when(shelterRepository.findFirstByTypeOfAnimal(eq(TypeOfAnimal.CAT))).thenReturn(shelter);

        catMenu.execute(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

        Mockito.verify(telegramBotSendMessage).sendMessage(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals("Пожалуйста выберите интересующее вас действие:", actual.getText());
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
        shelter.setInfo("Приют находится по адресу: город Астана, улица Сабита Муканова 2/1. Телефон: +7‒705‒587‒04‒04 https://2gis.kz/astana/geo/70000001082733265");
        when(shelterRepository.findFirstByTypeOfAnimal(eq(TypeOfAnimal.CAT))).thenReturn(shelter);

        catMenu.execute(update);

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

        Mockito.verify(telegramBotSendMessage).sendMessage(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();

        assertEquals("Приют находится по адресу: город Астана, улица Сабита Муканова 2/1. Телефон: +7‒705‒587‒04‒04 https://2gis.kz/astana/geo/70000001082733265", actual.getText());
        assertEquals("1", actual.getChatId());

    }


    @Test
    void createInlineKeyboardMarkupCorrect() {
        InlineKeyboardMarkup inlineKeyboardMarkupExp = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> button = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow4 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow5 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow6 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow7 = new ArrayList<>();

        InlineKeyboardButton informationAboutShelter = new InlineKeyboardButton("Информация о приюте для кошек");
        informationAboutShelter.setCallbackData(INFORMATION_ABOUT_SHELTER);
        buttonRow1.add(informationAboutShelter);

        InlineKeyboardButton contactInformationForAdmission = new InlineKeyboardButton("Контактные данные для оформления пропуска");
        contactInformationForAdmission.setCallbackData(CONTACT_INFORMATION_FOR_ADMISSION);
        buttonRow2.add(contactInformationForAdmission);

        InlineKeyboardButton safetyPrecautions = new InlineKeyboardButton("Техника безопасности на территории приюта");
        safetyPrecautions.setCallbackData(SAFETY_PRECAUTIONS);
        buttonRow3.add(safetyPrecautions);

        InlineKeyboardButton howTakePet = new InlineKeyboardButton("Как взять животное из приюта");
        howTakePet.setCallbackData(HOW_TAKE_PET);
        buttonRow4.add(howTakePet);

        InlineKeyboardButton sendReport = new InlineKeyboardButton("Прислать отчёт");
        sendReport.setCallbackData(SEND_REPORT);
        buttonRow5.add(sendReport);

        InlineKeyboardButton callVolunteer = new InlineKeyboardButton("Позвать волонтёра");
        callVolunteer.setCallbackData(CALL_VOLUNTEER);
        buttonRow6.add(callVolunteer);

        InlineKeyboardButton back = new InlineKeyboardButton("Назад");
        back.setCallbackData(BACK);
        buttonRow7.add(back);


        button.add(buttonRow1);
        button.add(buttonRow2);
        button.add(buttonRow3);
        button.add(buttonRow4);
        button.add(buttonRow5);
        button.add(buttonRow6);
        button.add(buttonRow7);

        inlineKeyboardMarkupExp.setKeyboard(button);


        assertEquals(catMenu.createInlineKeyboardMarkup(), inlineKeyboardMarkupExp);
        assertEquals(buttonRow1.get(0).getCallbackData(), INFORMATION_ABOUT_SHELTER);
        assertEquals(buttonRow2.get(0).getCallbackData(), CONTACT_INFORMATION_FOR_ADMISSION);
        assertEquals(buttonRow3.get(0).getCallbackData(), SAFETY_PRECAUTIONS);
        assertEquals(buttonRow4.get(0).getCallbackData(), HOW_TAKE_PET);
        assertEquals(buttonRow5.get(0).getCallbackData(), SEND_REPORT);
        assertEquals(buttonRow6.get(0).getCallbackData(), CALL_VOLUNTEER);
        assertEquals(buttonRow7.get(0).getCallbackData(), BACK);

    }
}