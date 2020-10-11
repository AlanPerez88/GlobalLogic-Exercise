package com.aperez.exercise.service.impl;

import com.aperez.exercise.dto.PhoneDto;
import com.aperez.exercise.entity.Phone;
import com.aperez.exercise.repository.PhoneRepository;
import com.aperez.exercise.util.Jwt;
import com.aperez.exercise.util.Util;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.UserException;
import com.aperez.exercise.repository.UserRepository;
import com.aperez.exercise.service.UserService;

import javax.swing.text.html.Option;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Jwt jwtUtil;

    @Autowired
    private Util util;

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
    public UserDto update(UserDto userDto) {
        UserDto newUserDto = findById(userDto.getId());
        newUserDto.setModified(new Date());
        User user = modelMapper.map(newUserDto, User.class);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        return modelMapper.map(user, UserDto.class);
    }


}
