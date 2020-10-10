package com.aperez.exercise.dto;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private String username;
    private String email;
    private String password;
    private Set<PhoneDto> phones = new HashSet<>();
    private Long id;
    private Date created;
    private Date updated;
    private Date lastLogin;
    private String token;
    private boolean isActive;

}
