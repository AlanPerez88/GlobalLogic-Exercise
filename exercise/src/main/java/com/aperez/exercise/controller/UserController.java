package com.aperez.exercise.controller;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.UserException;
import com.aperez.exercise.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("work", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) throws UserException {
        UserDto userSaved = userService.save(userDto);
        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDTO) throws UserException{
        UserDto userUpdated = userService.update(userDTO);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

}
