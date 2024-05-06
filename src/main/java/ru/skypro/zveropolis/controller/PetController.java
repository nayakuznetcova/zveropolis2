package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.exception.PetAlreadyExistsException;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.service.PetService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        if (petService.getPetById(pet.getId()).isPresent()) {
            throw new PetAlreadyExistsException("Такой питомец уже существует");
        }
        petService.addPet(pet);
    }

    @Operation(
            summary = "Получение информации о питомце"
    )

    @GetMapping("/getInfoPet/{id}")
    public ResponseEntity<Optional<Pet>> getPet(@PathVariable long id) {
        Optional<Pet> petToFind = petService.getPetById(id);
        if (petToFind.isEmpty()) {
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
        if (petService.getPetsAdopted(isAdopted,typeOfAnimal).isEmpty()) {
            return ResponseEntity.notFound().build();
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
    @Operation(
            summary = "Отправить животное под опеку"
    )
    @PutMapping ("/adoptPet/{id}")
    public ResponseEntity <Pet> adoptPet (@PathVariable long id, @RequestBody Users user) {

        if (petService.petToAdopt(id,user)==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(petService.petToAdopt(id,user));
    }

}
