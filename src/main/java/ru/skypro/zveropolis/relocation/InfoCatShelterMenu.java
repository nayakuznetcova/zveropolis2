package ru.skypro.zveropolis.relocation;

import liquibase.pro.packaged.S;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

@Service
public class InfoCatShelterMenu implements State{
    private final TelegramBotSendMessage telegramBotSendMessage;
    private final ShelterRepository shelterRepository;
    private final SubscriberRepository subscriberRepository;
    private final Relocation relocation;
    private final String DATING_RULES = "DATING_RULES";
    private final String ADOPTION_DOCUMENTS = "ADOPTION_DOCUMENTS";
    private final String TRANSPORTATION_RECOMMENDATIONS = "TRANSPORTATION_RECOMMENDATIONS";
    private final String RECOMMENDATIONS_ARRANGING_BABY = "RECOMMENDATIONS_ARRANGING_BABY";
    private final String RECOMMENDATIONS_ARRANGING_ADULT = "RECOMMENDATIONS_ARRANGING_ADULT";
    private final String RECOMMENDATIONS_ARRANGING_WITH_FEATURES = "RECOMMENDATIONS_ARRANGING_WITH_FEATURES";
    private final String BACK_CAT_REPORT = "BACK_CAT_REPORT";
    private final String HOW_TAKE_PET = "HOW_TAKE_PET";

    public InfoCatShelterMenu(@Lazy TelegramBotSendMessage telegramBotSendMessage, ShelterRepository shelterRepository, SubscriberRepository subscriberRepository,@Lazy Relocation relocation) {
        this.telegramBotSendMessage = telegramBotSendMessage;
        this.shelterRepository = shelterRepository;
        this.subscriberRepository = subscriberRepository;
        this.relocation = relocation;
    }

    @Override
    public void execute(Update update) {
        if(update.hasCallbackQuery()){
            sendMessageAtCallback(update);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
        }
    }

    @Override
    public InlineKeyboardMarkup createInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> button = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow1 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow2 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow3 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow4 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow5 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow6 = new ArrayList<>();
        List<InlineKeyboardButton> buttonRow7 = new ArrayList<>();

        InlineKeyboardButton datingRules = new InlineKeyboardButton("Правила знакомства");
        datingRules.setCallbackData(DATING_RULES);
        buttonRow1.add(datingRules);

        InlineKeyboardButton adoptionDocuments = new InlineKeyboardButton("Документы для усыновления");
        adoptionDocuments.setCallbackData(ADOPTION_DOCUMENTS);
        buttonRow2.add(adoptionDocuments);

        InlineKeyboardButton transportationRecommendations = new InlineKeyboardButton("Рекомендации по транспортировке");
        transportationRecommendations.setCallbackData(TRANSPORTATION_RECOMMENDATIONS);
        buttonRow3.add(transportationRecommendations);

        InlineKeyboardButton recommendationsArrangingBaby = new InlineKeyboardButton("Рекомендации для котёнка");
        recommendationsArrangingBaby.setCallbackData(RECOMMENDATIONS_ARRANGING_BABY);
        buttonRow4.add(recommendationsArrangingBaby);

        InlineKeyboardButton recommendationsArrangingAdult = new InlineKeyboardButton("Рекомендации для взрослой кошки");
        recommendationsArrangingAdult.setCallbackData(RECOMMENDATIONS_ARRANGING_ADULT);
        buttonRow5.add(recommendationsArrangingAdult);

        InlineKeyboardButton recommendationsArrangingWithFeatures = new InlineKeyboardButton("Рекомендации для кошки с особенностями");
        recommendationsArrangingWithFeatures.setCallbackData(RECOMMENDATIONS_ARRANGING_WITH_FEATURES);
        buttonRow6.add(recommendationsArrangingWithFeatures);

        InlineKeyboardButton backCatReport = new InlineKeyboardButton("Назад");
        backCatReport.setCallbackData(BACK_CAT_REPORT);
        buttonRow7.add(backCatReport);

        button.add(buttonRow1);
        button.add(buttonRow2);
        button.add(buttonRow3);
        button.add(buttonRow4);
        button.add(buttonRow5);
        button.add(buttonRow6);
        button.add(buttonRow7);

        inlineKeyboardMarkup.setKeyboard(button);
        return inlineKeyboardMarkup;
    }

    public void sendMessageAtCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();
        Shelter firstByTypeOfAnimal = shelterRepository.findFirstByTypeOfAnimal(TypeOfAnimal.CAT);
        switch (data) {
            case HOW_TAKE_PET ->{
                telegramBotSendMessage.sendMessage(createSendMessage("Какой вопрос вас интересует?", chatId));
            }
            case DATING_RULES -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getDatingRules(), chatId));
            }
            case ADOPTION_DOCUMENTS -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getAdoptionDocuments(), chatId));
            }
            case TRANSPORTATION_RECOMMENDATIONS -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getTransportationRecommendations(), chatId));
            }
            case RECOMMENDATIONS_ARRANGING_BABY -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getRecommendationsArrangingBaby(), chatId));
            }
            case RECOMMENDATIONS_ARRANGING_ADULT -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getRecommendationsArrangingAdult(), chatId));
            }
            case RECOMMENDATIONS_ARRANGING_WITH_FEATURES -> {
                telegramBotSendMessage.sendMessage(createSendMessage(
                        firstByTypeOfAnimal.getRecommendationsArrangingWithFeatures(), chatId));
            }
            case BACK_CAT_REPORT -> {
                subscriberRepository.putStateBot(chatId, StateBot.CAT_MENU);
                State state = relocation.getState(chatId);
                state.execute(update);
            }
        }
    }

    private SendMessage createSendMessage(String text, Long chatId){
        SendMessage createSendMessage = new SendMessage();
        createSendMessage.setText(text);
        createSendMessage.setChatId(chatId);
        createSendMessage.setReplyMarkup(createInlineKeyboardMarkup());
        return createSendMessage;
    }
}