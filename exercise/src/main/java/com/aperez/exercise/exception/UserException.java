package com.aperez.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserException extends Exception {

    private static final long serialVersionUID = 1L;

    final Integer code;
    final String message;

}
