package com.aperez.exercise.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    private Long id;
    private Integer number;
    private Integer citycode;
    private Integer countrycode;
}
