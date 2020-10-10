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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Value("${format.email}")
    private String EMAIL_FORMAT;

    @Value("${format.password}")
    private String PASS_FORMAT;

    @Value("${format.phone}")
    private String PHONE_FORMAT;

    @Override
    public User save(UserDto userDto) throws UserException {
        log.info("Ejecucion metodo save user");
        if (userDto.getUsername().isEmpty())
            throw new UserException(1, "Username no puede estar vacío");
        if (userDto.getEmail().isEmpty())
            throw new UserException(2, "Email no puede estar vacío");
        if (userDto.getPassword().isEmpty())
            throw new UserException(3, "Password no puede estar vacía");
        if (!util.validateStringPatter(userDto.getEmail(), EMAIL_FORMAT ))
            throw new UserException(4, "Formato de Email inválido");
        if (!util.validateStringPatter(userDto.getPassword(), PASS_FORMAT ))
            throw new UserException(5, "Password debe contener (Una Mayúscula, letras minúsculas y dos números)");
        if (!util.validateStringPatter(userDto.getPassword(), PASS_FORMAT ))
            throw new UserException(5, "Password debe contener (Una Mayúscula, letras minúsculas y dos números)");




        //Validar correo ya existe
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()){
            throw new UserException(1, "Ya existe un registro con este Email: ".concat(userDto.getEmail()));
        }
        Map<String, Object> dataToEncripted = new HashMap<>();
        dataToEncripted.put("service", "createuser");
        String jwt = jwtUtil.makeToken(userDto.getUsername(), dataToEncripted);

        User userEntity = modelMapper.map(userDto, User.class);
        userEntity.setToken(jwt);
        userEntity.setActive(true);

//        userDto.getPhones().forEach(x -> {
//            Phone phone = modelMapper.map(x, Phone.class);
//            phone.setUserid(userEntity.getId());
//            phoneRepository.saveAndFlush(phone);
//
//        });

        return userRepository.save(userEntity);
    }




    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(e -> modelMapper.map(e, UserDto.class)).collect(Collectors.toList());
    }

}
