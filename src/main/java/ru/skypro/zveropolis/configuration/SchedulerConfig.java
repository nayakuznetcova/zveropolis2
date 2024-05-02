package ru.skypro.zveropolis.configuration;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.skypro.zveropolis.TelegramBotSendMessage;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.relocation.Relocation;
import ru.skypro.zveropolis.relocation.ReportCatMenu;
import ru.skypro.zveropolis.repository.PetRepository;
import ru.skypro.zveropolis.repository.PhotoRepository;
import ru.skypro.zveropolis.repository.ReportRepository;
import ru.skypro.zveropolis.repository.SubscriberRepository;

@EnableScheduling
class SchedulerConfig extends ReportCatMenu {
    public SchedulerConfig(SubscriberRepository subscriberRepository, Relocation relocation, TelegramBotSendMessage telegramBotSendMessage, ReportRepository reportRepository, PhotoRepository photoRepository, PetRepository petRepository) {
        super(subscriberRepository, relocation, telegramBotSendMessage, reportRepository, photoRepository, petRepository);
    }

    @Scheduled (cron = "0 0 21 2 * *", zone = "Europe/Moscow")
    public void warning2day(Update update, TelegramBotSendMessage telegramBotSendMessage) throws InterruptedException {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = createSendMessage("Дорогой усыновитель! пришлите отчет о состоянии животного. " +
                "Напоминаем: отчет отправляется ежедневно!", chatId);
        telegramBotSendMessage.sendMessage(sendMessage);
    }
    @Scheduled (cron = "0 0 21 3 * *", zone = "Europe/Moscow")
    public void warning2dayVolonteer(Update update) throws InterruptedException{
        Long chatId = update.getMessage().getChatId();
        if (!update.getMessage().hasText())
            new Users(createSendMessage("Дорогой волонтёр! Усыновитель не присылает отчет о состоянии животного " +
                    "более 2 дней. Обратитесь к усыновителю за обратной связью!", chatId));
    }
}
