package com.aperez.exercise.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String username;
    private String email;
    private String password;
    private List<PhoneDto> phones;

}
