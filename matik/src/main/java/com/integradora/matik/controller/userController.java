package com.integradora.matik.controller;

import com.integradora.matik.dto.userDto;
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


    @Autowired
    public userController(userService UserService, userRepo userRepo) {
        this.UserService = UserService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody userDto UserDto) {
        return new ResponseEntity<>(UserService.save(UserDto), HttpStatus.CREATED);
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
