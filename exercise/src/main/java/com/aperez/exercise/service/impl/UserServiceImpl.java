package com.aperez.exercise.service.impl;


import com.aperez.exercise.util.Jwt;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.UserException;
import com.aperez.exercise.repository.UserRepository;
import com.aperez.exercise.service.UserService;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Jwt jwtUtil;


    @Override
    public UserDto save(UserDto userDto) throws UserException {
        log.info("Ejecucion metodo save user");
        Map<String, Object> map = new HashMap<>();
        map.put("password", userDto.getPassword());
        String jwt = jwtUtil.makeToken(userDto.getEmail(), map);
        User userEntity = modelMapper.map(userDto, User.class);
        userEntity.setToken(jwt);
        userEntity.setActive(true);
        User userSaved = userRepository.save(userEntity);
        return modelMapper.map(userSaved, UserDto.class);
    }


    @Override
    public UserDto update(UserDto userDto) throws UserException {
        log.info("Ejecucion metodo update user");
        UserDto userDtoFind = findById(userDto.getId());
        User userSaved = new User();
        if(userDtoFind.getEmail() != null) {
            userDto.setModified(new Date());
            userSaved = userRepository.save(modelMapper.map(userDto, User.class));
        }else{
            throw new UserException("No se encuentra Usuario");
        }
        return modelMapper.map(userSaved, UserDto.class);
    }

    @Override
    public UserDto findById(Long id) throws UserException {
        log.info("Ejecucion metodo findById user");
        User user = userRepository.findById(id).orElse(new User());
        return modelMapper.map(user, UserDto.class);
    }


}
