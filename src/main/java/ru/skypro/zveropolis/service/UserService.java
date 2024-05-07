package ru.skypro.zveropolis.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    public Users createUser(Users user) {
        return userRepository.save(user);
    }


    public Optional<Users> getUserInfo(long id) {
        return userRepository.findById(id);
    }

    public Users editUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<Users> getVolonteers(){
        return userRepository.getUsersByVolunteerIsTrue();
    }
}