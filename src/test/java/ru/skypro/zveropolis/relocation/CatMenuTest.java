package ru.skypro.zveropolis.relocation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CatMenuTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShelterRepository shelterRepository;

    @MockBean
    private SubscriberRepository subscriberRepository;

    @Autowired
    private Relocation relocation;

    @Autowired
    private TelegramBotSendMessage telegramBotSendMessage;
//    @Autowired
//    private CatMenu catMenu;

    @InjectMocks
    private CatMenu catMenu;

    private final String INFORMATION_ABOUT_SHELTER = "INFORMATION_ABOUT_SHELTER";
    private final String HOW_TAKE_PET = "HOW_TAKE_PET";
    private final String SEND_REPORT = "SEND_REPORT";
    private final String CALL_VOLUNTEER = "CALL_VOLUNTEER";
    private final String BACK = "BACK";
    private final String CAT_SHELTER = "CAT_SHELTER";
    private final String BACK_CAT_REPORT = "BACK_CAT_REPORT";




//    @Test
//    void execute(Update update) {
//
//    }

//    public void execute(Update update) {
//        if(update.hasCallbackQuery()){
//            sendMessageAtCallback(update);
//
//        } else if (update.hasMessage() && update.getMessage().hasText()) {
//            //sendMessageAtText(update);
//        }
//    }

    @Test
    void createInlineKeyboardMarkupCorrect () {
        InlineKeyboardMarkup inlineKeyboardMarkupExp = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> buttonsExp = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow4 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow5 = new ArrayList<>();

        InlineKeyboardButton informationAboutShelter = new InlineKeyboardButton("Информация о приюте для кошек");
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


        assertEquals(catMenu.createInlineKeyboardMarkup(),inlineKeyboardMarkupExp);
        assertEquals(buttonRow1.get(0).getCallbackData(),INFORMATION_ABOUT_SHELTER);
        assertEquals(buttonRow2.get(0).getCallbackData(),HOW_TAKE_PET);
        assertEquals(buttonRow3.get(0).getCallbackData(),SEND_REPORT);
        assertEquals(buttonRow4.get(0).getCallbackData(),CALL_VOLUNTEER);
        assertEquals(buttonRow5.get(0).getCallbackData(),BACK);

    }
    @Test
    void createSendMessageCorrect(){
        SendMessage expSendMessage = new SendMessage("1","123");
        SendMessage actSendMessage = new SendMessage();
        actSendMessage.setText("123");
        actSendMessage.setChatId(expSendMessage.getChatId());
        assertEquals(expSendMessage,actSendMessage);

    }
    /*
    в таком стиле писать? не очень понимаю пока как писать тесты на следующий метод
    */
    @Test
    void sendMessageAtCallbackButtonsCorrect(Update update) {
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        String data = update.getCallbackQuery().getData();
//        Shelter CAT_SHELTER_TEST = new Shelter();
//        CAT_SHELTER_TEST.setGreeting("hello");
//        CAT_SHELTER_TEST.setInfo("info");
//        CAT_SHELTER_TEST.setDatingRules("rules");
//
//        when(shelterRepository.findFirstByTypeOfAnimal(any(TypeOfAnimal.class))).thenReturn(CAT_SHELTER_TEST);
//
//        String exp1 = CAT_SHELTER_TEST.getGreeting();
//        Message act2 = telegramBotSendMessage.sendMessage(catMenu.createSendMessage (CAT_SHELTER_TEST.getGreeting(),chatId));
//        assertEquals(exp1,act2.getText());
  }

}



//        Shelter firstByTypeOfAnimal = shelterRepository.findFirstByTypeOfAnimal(TypeOfAnimal.CAT);


//
//    public void sendMessageAtCallback(Update update) {
//        Long chatId = update.getCallbackQuery().getMessage().getChatId();
//        String data = update.getCallbackQuery().getData();
//        Shelter firstByTypeOfAnimal = shelterRepository.findFirstByTypeOfAnimal(TypeOfAnimal.CAT);
//        switch (data) {
//            case CAT_SHELTER -> {
//                telegramBotSendMessage.sendMessage(createSendMessage(
//                        firstByTypeOfAnimal.getGreeting(), chatId));
//            }
//            case INFORMATION_ABOUT_SHELTER -> {
//                telegramBotSendMessage.sendMessage(createSendMessage(
//                        firstByTypeOfAnimal.getInfo(), chatId
//                ));
//            }
//            case HOW_TAKE_PET -> {
//                telegramBotSendMessage.sendMessage(createSendMessage(
//                        firstByTypeOfAnimal.getDatingRules(), chatId
//                ));
//            }
//            case SEND_REPORT -> {
//                subscriberRepository.putStateBot(chatId, StateBot.REPORT_CAT_MENU);
//                State state = relocation.getState(chatId);
//                state.execute(update);
//            }
//            case CALL_VOLUNTEER -> {
//
//            }
//            case BACK -> {
//                subscriberRepository.putStateBot(chatId, StateBot.START_MENU);
//                State state = relocation.getState(chatId);
//                state.execute(update);
//            }
//            case BACK_CAT_REPORT ->{
//                telegramBotSendMessage.sendMessage(createSendMessage("Вы вернулись в меню кошачьего приюта", chatId));
//            }
//        }
//}