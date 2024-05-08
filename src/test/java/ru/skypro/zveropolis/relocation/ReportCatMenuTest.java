package ru.skypro.zveropolis.relocation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.repository.PetRepository;
import ru.skypro.zveropolis.repository.PhotoRepository;
import ru.skypro.zveropolis.repository.ReportRepository;
import ru.skypro.zveropolis.repository.SubscriberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ReportCatMenuTest {

    @Mock
    private TelegramBotSendMessage telegramBotSendMessage;

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private Relocation relocation;

    @InjectMocks
    private ReportCatMenu reportCatMenu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reportCatMenu = new ReportCatMenu(
                subscriberRepository,
                relocation,
                telegramBotSendMessage,
                mock(ReportRepository.class),
                mock(PhotoRepository.class),
                mock(PetRepository.class)
        );
    }


    @Test
    public void testHandleDailyReportForm_PhotoWithoutText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.hasPhoto()).thenReturn(true);
        when(message.hasText()).thenReturn(false);

        reportCatMenu.handleDailyReportForm(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }

    @Test
    public void testHandleDailyReportForm_TextWithoutPhoto() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.hasPhoto()).thenReturn(false);
        when(message.hasText()).thenReturn(true);

        reportCatMenu.handleDailyReportForm(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }

    @Test
    public void testHandleDailyReportForm_IncompleteReport() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.hasPhoto()).thenReturn(true);
        when(message.hasText()).thenReturn(true);

        reportCatMenu.handleDailyReportForm(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }

    @Test
    public void testSendMessageAtCallback() {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Message message = mock(Message.class);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        when(callbackQuery.getData()).thenReturn("BACK_CAT_REPORT");

        State state = mock(State.class);
        when(relocation.getState(123L)).thenReturn(state);
        reportCatMenu.sendMessageAtCallback(update);

        verify(subscriberRepository).putStateBot(123L, StateBot.CAT_MENU);
        verify(state).execute(update);
    }

    @Test
    public void testCongratulateAdopter() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        reportCatMenu.congratulateAdopter(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }

    @Test
    public void testNotifyAdditionalTrialPeriod() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        Long chatId = 123L;
        when(message.getChatId()).thenReturn(chatId);

        reportCatMenu.notifyAdditionalTrialPeriod(update);

        verify(telegramBotSendMessage).sendMessage(any(SendMessage.class));
    }

    @Test
    public void testNotifyUnsuccessfulTrialPeriod() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        reportCatMenu.notifyUnsuccessfulTrialPeriod(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }

    @Test
    public void testCallVolunteer() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        reportCatMenu.callVolunteer(update);

        verify(telegramBotSendMessage).sendMessage(any());
    }
}