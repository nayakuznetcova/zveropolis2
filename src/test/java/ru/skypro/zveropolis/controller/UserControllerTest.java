package ru.skypro.zveropolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.skypro.zveropolis.model.Users;

import ru.skypro.zveropolis.repository.UserRepository;

import ru.skypro.zveropolis.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private UserService userService;


    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createVolunteer() throws Exception {

        long id = 20L;
        String name = "test";
        boolean isVolunteer = true;

        Users test = new Users();
        test.setChatId(id);
        test.setFirstName(name);
        test.setVolunteer(isVolunteer);

        when(userRepository.save(any(Users.class))).thenReturn(test);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(objectMapper.writeValueAsString(test))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getVolunteerInfo() {
    }

    @Test
    void editVolunteerInfo() {
    }

    @Test
    void deleteVolunteer() {
    }
}