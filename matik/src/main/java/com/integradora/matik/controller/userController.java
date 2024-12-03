package com.integradora.matik.controller;

import com.integradora.matik.dto.userDto;
import com.integradora.matik.repository.ReserveRepo;
import com.integradora.matik.repository.userRepo;
import com.integradora.matik.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin

public class userController {

    private final userService UserService;
    private ReserveRepo reserveService;


    @Autowired
    public userController(userService UserService, userRepo userRepo) {
        this.UserService = UserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody userDto loginRequest) {
        try {
            userDto user = UserService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return new ResponseEntity<>(user, HttpStatus.OK); // Retorna el usuario encontrado
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED); // Credenciales inválidas
        } catch (Exception e) {
            return new ResponseEntity<>("Error en el inicio de sesión", HttpStatus.INTERNAL_SERVER_ERROR); // Error general
        }
    }

    @PostMapping
    // Registro de usuario
    public ResponseEntity<Object> save(@RequestBody userDto UserDto) {
        try {
            Integer userId = UserService.save(UserDto);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Manejo del error de correo duplicado
            if (e.getMessage().contains("correo ya está en uso")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // Código 409
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error al procesar la solicitud.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping()
    public List<userDto> getAllUsers() {
        return UserService.findAllUsers();
    }


    @GetMapping("/{id}")
    public userDto getUserById(@PathVariable int id) {
        return UserService.findUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody userDto UserDto) {
        userDto updatedUser = UserService.updateUser(id, UserDto);

        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        boolean isDeleted = UserService.deleteUserById(id);

        if (isDeleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


}
