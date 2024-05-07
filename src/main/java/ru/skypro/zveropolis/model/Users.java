package ru.skypro.zveropolis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
//@NoArgsConstructor
//@Table(schema = "users")
public class Users {
    @Id
    private long chatId;
    private String firstName;
    private String username;
    private String phoneNumber;
    private boolean isVolunteer;

    public Users(SendMessage sendMessage) {
    }

    public Users() {

    }

//    @OneToMany
//    @JoinColumn(name = "pet_id")
//    private Pet pet;
}
