package com.aperez.exercise.service.impl;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.JwtException;
import com.aperez.exercise.repository.UserRepository;
import com.aperez.exercise.service.LoginService;
import com.aperez.exercise.util.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jwt jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto login(UserDto userDto, String authorization) throws JwtException {

        jwtUtil.validateToken(authorization, userDto);
        User user = userRepository.findByEmailPassword(userDto.getEmail(), userDto.getPassword());
        if (user != null) {
            user.setLastLogin(new Date());
            user.setModified(new Date());
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new JwtException("Usuario no encontrado");
        }

        return modelMapper.map(user, UserDto.class);
    }


    @Override
    public UserDto logout(UserDto userDto, String authorization) throws JwtException {

        jwtUtil.validateToken(authorization, userDto);
        User user = userRepository.findByEmailPassword(userDto.getEmail(), userDto.getPassword());
        user.setModified(new Date());
        user.setActive(false);
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }


    @Override
    public UserDto getToken(UserDto userDto) throws JwtException {

        User user = userRepository.findByEmailPassword(userDto.getEmail(), userDto.getPassword());
        if (user != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("password", userDto.getPassword());
            String jwt = jwtUtil.makeToken(userDto.getEmail(), map);
            user.setModified(new Date());
            user.setToken(jwt);
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new JwtException("Usuario debe estar creado para solicitar token");
        }


        return modelMapper.map(user, UserDto.class);
    }
}
