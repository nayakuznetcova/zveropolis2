package ru.skypro.zveropolis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
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

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    void createVolunteerCorrect() throws Exception {

        long id = 20L;
        String firstName = "test";
        boolean isVolunteer = true;

        Users userTest = new Users();
        userTest.setChatId(id);
        userTest.setFirstName(firstName);
        userTest.setVolunteer(isVolunteer);


        when(userRepository.save(any(Users.class))).thenReturn(userTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(objectMapper.writeValueAsString(userTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getVolunteerInfoCorrect() throws Exception {
        long id = 20L;
        String firstName = "test";
        boolean isVolunteer = true;

        Users userTest = new Users();
        userTest.setChatId(id);
        userTest.setFirstName(firstName);
        userTest.setVolunteer(isVolunteer);


        when(userService.getUserInfo(userTest.getChatId())).thenReturn(Optional.of(userTest));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/" + id)
                        .content(objectMapper.writeValueAsString(Optional.of(userTest)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is(userTest.getFirstName())));

    }

    @Test
    void editVolunteerInfoCorrect() throws Exception {
        long id = 20L;
        String firstName = "test";
        boolean isVolunteer = true;
        String newName = "new";

        Users userTest = new Users();
        userTest.setChatId(id);
        userTest.setFirstName(firstName);
        userTest.setVolunteer(isVolunteer);

        Users newUser = new Users();
        newUser.setChatId(id);
        newUser.setFirstName(newName);
        newUser.setVolunteer(isVolunteer);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("firstName", newName);
        jsonObject.put("isVolunteer", isVolunteer);

        when(userRepository.save(any(Users.class))).thenReturn(newUser);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(id))
                .andExpect(jsonPath("$.firstName").value(newName));
    }

    @Test
    void deleteVolunteerCorrect() throws Exception {
        long id = 20L;
        String firstName = "test";
        boolean isVolunteer = true;

        Users userTest = new Users();
        userTest.setChatId(id);
        userTest.setFirstName(firstName);
        userTest.setVolunteer(isVolunteer);


        when(userRepository.save(any(Users.class))).thenReturn(userTest);
        when(userService.getUserInfo(id)).thenReturn(Optional.of(userTest));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}