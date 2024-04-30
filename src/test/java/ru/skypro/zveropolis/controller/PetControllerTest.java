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

import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.PetRepository;
import ru.skypro.zveropolis.service.PetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @SpyBean
    private PetService petService;


    @InjectMocks
    private PetController petController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addPetCorrect() throws Exception {

        long id = 20L;
        String name = "test";

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);

        when(petRepository.save(any(Pet.class))).thenReturn(petTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pet/addPet")
                        .content(objectMapper.writeValueAsString(petTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void getPetInfoCorrect() throws Exception {
        long id = 20L;
        String name = "test";

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);


        when(petService.getPetById(petTest.getId())).thenReturn(Optional.of(petTest));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pet/getInfoPet/" + id)
                        .content(objectMapper.writeValueAsString(Optional.of(petTest)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(petTest.getName())));

    }

    @Test
    void updatePetCorrect() throws Exception {
        long id = 20L;
        String name = "test";
        String newName = "new";

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);

        Pet petUpdate = new Pet();
        petUpdate.setId(id);
        petUpdate.setName(newName);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", newName);

        when(petRepository.save(any(Pet.class))).thenReturn(petUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/pet/updateInfoPet")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    void deletePetCorrect() throws Exception {
        Long id = 20L;
        String name = "test";

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);


        when(petRepository.save(any(Pet.class))).thenReturn(petTest);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pet/deletePet/" + petTest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getListOfTypeOfAnimalCorrect() throws Exception {
        long id = 20L;
        String name = "test";
        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);
        petTest.setTypeOfAnimal(TypeOfAnimal.CAT);
        List<Pet> act = new ArrayList<>();
        act.add(petTest);
        List<Pet> exp = new ArrayList<>();
        exp.add(petTest);
        when(petRepository.findAllByTypeOfAnimal(TypeOfAnimal.CAT)).thenReturn(act);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/pet/getListOf/" + petTest.getTypeOfAnimal())
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(act.size(), exp.size());
        for (int i = 0; i < act.size(); i++) {
            assertEquals(exp.get(i).getName(), act.get(i).getName());
        }
    }


    @Test
    void getListOfAdoptedPetsCorrect() throws Exception {
        long id = 20L;
        String name = "test";
        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);
        petTest.setAdopted(true);
        petTest.setTypeOfAnimal(TypeOfAnimal.CAT);
        List<Pet> act = new ArrayList<>();
        act.add(petTest);
        List<Pet> exp = new ArrayList<>();
        exp.add(petTest);
        when(petService.getPetsAdopted(true, TypeOfAnimal.CAT)).thenReturn(act);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/pet/getListOfAdopted/" + petTest.getTypeOfAnimal())
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(act.size(), exp.size());
        for (int i = 0; i < act.size(); i++) {
            assertEquals(exp.get(i).getName(), act.get(i).getName());
        }
    }

    @Test
    void getListOfAllPetsCorrect() throws Exception {
        long id = 20L;
        String name = "test";
        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);
        petTest.setTypeOfAnimal(TypeOfAnimal.CAT);
        List<Pet> act = new ArrayList<>();
        act.add(petTest);
        List<Pet> exp = new ArrayList<>();
        exp.add(petTest);
        when(petService.getAll()).thenReturn(act);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/pet/getListOfAllPets")
                .accept(MediaType.APPLICATION_JSON));

        assertEquals(act.size(), exp.size());
        for (int i = 0; i < act.size(); i++) {
            assertEquals(exp.get(i).getName(), act.get(i).getName());
        }
    }

    @Test
    void adoptPetCorrect() throws Exception {
        Long id = 20L;
        String name = "test";
        boolean isNotAdopted = false;
        boolean isAdopted = true;


        Users user = new Users();
        user.setFirstName("davr");

        Pet petTest = new Pet();
        petTest.setId(id);
        petTest.setName(name);
        petTest.setAdopted(isNotAdopted);

        Pet petUpdate = new Pet();
        petUpdate.setId(id);
        petUpdate.setAdopted(isAdopted);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("isAdopted", isAdopted);

        when(petRepository.findById(id)).thenReturn(Optional.of(petTest));
        when(petRepository.save(any(Pet.class))).thenReturn(petUpdate);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/pet/adoptPet/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.adopted").value(isAdopted));
    }

}