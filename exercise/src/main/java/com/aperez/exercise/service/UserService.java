package com.aperez.exercise.service;

import java.util.List;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.exception.UserException;

public interface UserService {

    UserDto save(UserDto userDto) throws UserException;

    List<UserDto> findAll();

}
