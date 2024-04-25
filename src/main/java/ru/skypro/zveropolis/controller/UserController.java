package ru.skypro.zveropolis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.service.UserService;

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
                    responseCode = "400",
                    description = "Пользователь не является волонтером"
            )
    })
    @PostMapping
    public ResponseEntity<Users> createVolonteer(Users user) {
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
    public ResponseEntity<Optional<Users>> getVolonteerInfo(@PathVariable long id) {
        Optional<Users> volonteerToFind = userService.getUserInfo(id);
        if (volonteerToFind.get().isVolunteer()) {
            return ResponseEntity.ok(volonteerToFind);
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
    public ResponseEntity<Users> editVolonteerInfo(Users user) {
        Users volonteerToEdit = userService.editUser(user);
        if (volonteerToEdit.isVolunteer()) {
            return ResponseEntity.ok(volonteerToEdit);
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
    public ResponseEntity deleteVolonteer(@PathVariable long id) {
        Optional<Users> volonteerToDelete = userService.getUserInfo(id);
        if (volonteerToDelete.get().isVolunteer()) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}

