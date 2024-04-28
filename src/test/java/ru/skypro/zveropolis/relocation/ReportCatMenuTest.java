package ru.skypro.zveropolis.relocation;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

    @Mock
    private ReportCatMenu reportCatMenu;

    @Test
    public void testTextAndPhoto() {
        // Подготовка макетов объектов
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message); // Устанавливаем ожидаемое поведение для метода getMessage()

        // Задаем ожидаемое поведение для метода getChatId() у макета Message
        when(message.getChatId()).thenReturn(123L);
        when(message.getCaption()).thenReturn(null);

        // Создаем макет объекта TelegramBotSendMessage
        TelegramBotSendMessage telegramBotSendMessageMock = mock(TelegramBotSendMessage.class);

        // Создаем экземпляр класса ReportCatMenu с макетами объектов в качестве зависимостей
        ReportCatMenu reportCatMenu = new ReportCatMenu(
                mock(SubscriberRepository.class),
                mock(Relocation.class),
                telegramBotSendMessageMock,
                mock(ReportRepository.class),
                mock(PhotoRepository.class),
                mock(PetRepository.class)
        );

        // Проверяем, что метод sendMessage был вызван один раз у макета telegramBotSendMessageMock с любым аргументом SendMessage
        verify(telegramBotSendMessageMock, times(1)).sendMessage(any(SendMessage.class));
    }

    @Test
    public void sendMessageAtCallback() {
        // Подготовка макетов объектов
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Message message = mock(Message.class);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        when(callbackQuery.getData()).thenReturn("BACK_CAT_REPORT");

        // Подготовка макета State
        State state = mock(State.class);

        // Установка ожидаемого поведения для метода getState
        when(relocation.getState(123L)).thenReturn(state);

        // Создание объекта класса, который тестируем
        ReportCatMenu reportCatMenu = new ReportCatMenu(
                subscriberRepository,
                relocation,
                telegramBotSendMessage,
                mock(ReportRepository.class),
                mock(PhotoRepository.class),
                mock(PetRepository.class)
        );

        // Действие
        reportCatMenu.sendMessageAtCallback(update);

        // Проверка
        verify(subscriberRepository).putStateBot(123L, StateBot.CAT_MENU);
        verify(state).execute(update);
    }

    @Test
    public void testCreateSendMessage() {

        String text = "Тестовое сообщение";
        Long chatId = 123L;

        ReportCatMenu reportCatMenu = new ReportCatMenu(mock(SubscriberRepository.class), mock(Relocation.class), mock(TelegramBotSendMessage.class),
                mock(ReportRepository.class), mock(PhotoRepository.class), mock(PetRepository.class));

        SendMessage sendMessage = reportCatMenu.createSendMessage(text, chatId);

        assertEquals(text, sendMessage.getText());
        assertEquals(chatId, sendMessage.getChatId());
        assertNotNull(sendMessage.getReplyMarkup());
    }

    @Test
    public void testCreateSendMessageNotKeyboard() {

        String text = "Тестовое сообщение";
        Long chatId = 123L;

        ReportCatMenu reportCatMenu = new ReportCatMenu(mock(SubscriberRepository.class), mock(Relocation.class), mock(TelegramBotSendMessage.class),
                mock(ReportRepository.class), mock(PhotoRepository.class), mock(PetRepository.class));

        SendMessage sendMessage = reportCatMenu.createSendMessageNotKeyboard(text, chatId);

        assertEquals(text, sendMessage.getText());
        assertEquals(chatId, sendMessage.getChatId());
    }
}
