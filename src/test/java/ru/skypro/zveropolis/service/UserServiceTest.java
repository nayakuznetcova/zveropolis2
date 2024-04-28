package ru.skypro.zveropolis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService out;

    @Mock
    UserRepository repository;

    Users USER_1 = new Users();


    @BeforeEach
    void setUp() {
        USER_1.setChatId(1L);
        USER_1.setFirstName("volodia");
    }

    @Test
    void createUserCorrect() {
        Mockito.when(repository.save(USER_1)).thenReturn(USER_1);
        out.createUser(USER_1);
        Mockito.verify(repository, Mockito.times(1)).save(USER_1);
        assertEquals(USER_1, out.createUser(USER_1));
    }

    @Test
    void getUserInfoCorrect() {
        long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(USER_1));
        out.getUserInfo(id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        assertEquals(Optional.of(USER_1), out.getUserInfo(id));
    }

    @Test
    void editUser() {
        Mockito.when(repository.save(USER_1)).thenReturn(USER_1);
        out.editUser(USER_1);
        Mockito.verify(repository, Mockito.times(1)).save(USER_1);
        assertEquals(USER_1, out.editUser(USER_1));
    }

    @Test
    void deleteUser() {
        long id = 2L;
        Mockito.doNothing().when(repository).deleteById(id);
        out.deleteUser(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }
}