package ru.skypro.zveropolis.model;

import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.persistence.*;

@Entity
@Data
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String greeting;
    private String info;
    private String datingRules;
    private String adoptionDocuments;
    private String transportationRecommendations;
    private String recommendationsArrangingBaby;
    private String recommendationsArrangingAdult;
    private String recommendationsArrangingWithFeatures;
    @Enumerated(EnumType.STRING)
    private TypeOfAnimal typeOfAnimal;

    public Shelter(SendMessage sendMessage) {

    }
}
