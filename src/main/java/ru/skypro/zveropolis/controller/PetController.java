package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Operation(
            summary = "Добавление питомца в приют"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Питомец добавлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PostMapping("/addPet")
    public void addPet(@RequestBody Pet pet) {
        petService.addPet(pet);
    }

    @Operation(
            summary = "Получение информации о питомце"
    )

    @GetMapping("/getInfoPet/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable long id) {
        Pet petToFind = petService.getPetById(id);
        if (petToFind == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @Operation (
            summary = "Коррекция информации о питомце"
    )

    @PutMapping("/updateInfoPet")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        Pet petToUpdate = petService.updatePet(pet);
        if (petToUpdate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(petToUpdate);

    }
    @Operation(
            summary = "Удаление данных о питомце"
    )

    @DeleteMapping ("/deletePet/{id}")
    public  ResponseEntity <Void> deletePet (@PathVariable long id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка кошек или собак"
    )

    @GetMapping("/getListOf/{typeOfAnimal}")
    public ResponseEntity<List<Pet>> getListOf(TypeOfAnimal typeOfAnimal) {
        if (typeOfAnimal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(petService.getListOf(typeOfAnimal));
    }
    @Operation(

            summary = "Получение списка питомцев под опекой"
    )

    @GetMapping("/getListOfAdopted/{typeOfAnimal}")
    public ResponseEntity<List<Pet>> getListOfAdoptedPets(boolean isAdopted, @PathVariable TypeOfAnimal typeOfAnimal) {
        if (typeOfAnimal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(petService.getPetsAdopted(isAdopted,typeOfAnimal));
    }
    @Operation(
            summary = "Получение списка всех питомцев"
    )

    @GetMapping("/getListOfAllPets")
    public ResponseEntity <List<Pet>> getListOfAllPets () {
        if (petService.getAll().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petService.getAll());
    }
}
