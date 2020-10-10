package com.aperez.exercise.controller;

import com.aperez.exercise.dto.UserDto;
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
    UserService service;

    @GetMapping
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("work", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) throws UserException {
        return new ResponseEntity<>(service.save(userDto), HttpStatus.OK);
    }

}
