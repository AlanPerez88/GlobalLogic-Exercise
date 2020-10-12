package com.aperez.exercise.service;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.exception.UserException;

public interface UserService {

    UserDto save(UserDto userDto) throws UserException;
    UserDto update(UserDto userDto) throws UserException;;
    UserDto findById(Long id) throws UserException;;


}
