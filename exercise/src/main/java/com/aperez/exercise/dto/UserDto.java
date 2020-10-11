package com.aperez.exercise.dto;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String token;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private boolean isActive;
    private Set<PhoneDto> phones = new HashSet<>();


}
