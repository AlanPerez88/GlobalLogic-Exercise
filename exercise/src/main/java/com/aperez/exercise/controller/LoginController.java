package com.aperez.exercise.controller;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.JwtException;
import com.aperez.exercise.exception.LoginException;
import com.aperez.exercise.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto,
                                      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true)
                                              String authorization) throws JwtException {
        return new ResponseEntity<>(loginService.login(userDto, authorization), HttpStatus.OK);
    }


    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> logout(@RequestBody UserDto userDto,
                                         @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true)
                                                 String authorization) throws JwtException {
        return new ResponseEntity<>(loginService.logout(userDto, authorization), HttpStatus.OK);
    }


}
