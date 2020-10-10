package com.aperez.exercise.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.UserException;
import com.aperez.exercise.repository.UserRepository;
import com.aperez.exercise.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto save(UserDto userDto) throws UserException {
        log.info("Ejecucion metodo save user");
        if (userDto.getUsername().isEmpty())
            throw new UserException(1, "Nombre no puede estar vacío");
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(repository.save(user), UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = repository.findAll();
        return userList.stream().map(e -> modelMapper.map(e, UserDto.class)).collect(Collectors.toList());
    }

}
