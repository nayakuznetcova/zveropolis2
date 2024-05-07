package ru.skypro.zveropolis.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private boolean withLimitedOpportunities;
    private boolean isAdopted;
    private int timer;
    @Enumerated(EnumType.STRING)
    private TypeOfAnimal typeOfAnimal;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
