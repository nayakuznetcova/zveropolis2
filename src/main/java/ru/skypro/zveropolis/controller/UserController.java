package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.service.PetService;
import ru.skypro.zveropolis.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(
            summary = "Добавление волонтера"
    )

    @ApiResponses(value = {

            @ApiResponse(

                    responseCode = "200",
                    description = "Волонтер успешно добавлен"

            ),
            @ApiResponse(

                    responseCode = "400",
                    description = "Пользователь не является волонтером"
            )
    })
    @PostMapping
    public ResponseEntity<Users> createVolunteer(Users user) {
        if (user.isVolunteer()) {
            return ResponseEntity.ok(userService.createUser(user));
        }
        return ResponseEntity.badRequest().build();
    }


    @Operation(
            summary = "Получение информации о волонтере"
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "400",
                    description = "Данного волонтера не найдено"
            )
    })

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Users>> getVolunteerInfo(@PathVariable long id) {
        Optional<Users> volunteerToFind = userService.getUserInfo(id);
        if (volunteerToFind.get().isVolunteer()) {
            return ResponseEntity.ok(volunteerToFind);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Коррекция информации о волонтере"
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "400",
                    description = "Невозможно откорректировать информацию, пользователь не является волонтером"
            )
    })

    @PutMapping
    public ResponseEntity<Users> editVolunteerInfo(Users user) {
        Users volunteerToEdit = userService.editUser(user);
        if (volunteerToEdit.isVolunteer()) {
            return ResponseEntity.ok(volunteerToEdit);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(
            summary = "Удаление волонтера"
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "400",
                    description = "Пользователь не является волонтером"
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVolunteer(@PathVariable long id) {
        Optional<Users> volunteerToDelete = userService.getUserInfo(id);
        if (volunteerToDelete.get().isVolunteer()) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}

