package com.aperez.exercise.service;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.exception.JwtException;

public interface LoginService {

    UserDto login(UserDto userDto, String authorization) throws JwtException;
    UserDto logout(UserDto userDto, String authorization) throws JwtException;
    UserDto getToken(UserDto userDto) throws JwtException;
}
