package com.aperez.exercise.service;

import java.util.List;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.UserException;

public interface UserService {

    User save(UserDto userDto) throws UserException;

    List<UserDto> findAll();

}
