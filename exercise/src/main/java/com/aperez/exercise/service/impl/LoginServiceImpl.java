package com.aperez.exercise.service.impl;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.entity.User;
import com.aperez.exercise.exception.JwtException;
import com.aperez.exercise.exception.LoginException;
import com.aperez.exercise.repository.UserRepository;
import com.aperez.exercise.service.LoginService;
import com.aperez.exercise.util.Jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    User user = userRepository.findByEmailPassword(userDto.getEmail(),userDto.getPassword());
    user.setLastLogin(new Date());
    user.setModified(new Date());
    userRepository.save(user);

     return modelMapper.map(user, UserDto.class);
    }


    @Override
    public UserDto logout(UserDto userDto, String authorization) throws JwtException {

        jwtUtil.validateToken(authorization, userDto);
        User user = userRepository.findByEmailPassword(userDto.getEmail(),userDto.getPassword());
        user.setModified(new Date());
        user.setActive(false);
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }
}
