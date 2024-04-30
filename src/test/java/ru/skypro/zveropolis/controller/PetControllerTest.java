package ru.skypro.zveropolis.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.repository.PetRepository;
import ru.skypro.zveropolis.service.PetService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @SpyBean
    private PetService petService;


    @InjectMocks
    private PetController petController;

    @Test
    void addPet() {

        Long id = 20L;
        String name = "test";
        JSONObject userObject = new JSONObject();
        userObject.put("name", name);
        userObject.put("id", id);

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);


//        Long id = 20L;
//        String name = "test";
//        String color = "test";
//        JSONObject userObject = new JSONObject();
//        userObject.put("name", name);
//        userObject.put("color", color);
//
//        Faculty facultyTest = new Faculty();
//        facultyTest.setId(id);
//        facultyTest.setName(name);
//        facultyTest.setColor(color);
//
//        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyTest);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/faculty/createFaculty")
//                        .content(userObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.color").value(color));
//    }
    }

    @Test
    void getPet() {
    }

    @Test
    void updatePet() {
    }

    @Test
    void deletePet() {
    }

    @Test
    void getListOf() {
    }

    @Test
    void getListOfAdoptedPets() {
    }

    @Test
    void getListOfAllPets() {
    }
}