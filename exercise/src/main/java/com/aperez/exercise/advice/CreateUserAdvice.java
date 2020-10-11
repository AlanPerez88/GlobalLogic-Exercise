package com.aperez.exercise.advice;

import java.util.*;

import com.aperez.exercise.exception.JwtException;
import com.aperez.exercise.exception.UserException;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class CreateUserAdvice {
    @ExceptionHandler({UserException.class})
    public ResponseEntity<Map<String, Object>> exampleException(UserException e) {
        log.error("Error: {}", e.getLocalizedMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", e.getCode());
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> generalException(Exception e) {
        log.error("Error: {}", e.getLocalizedMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 9999);
        response.put("message", "Error de servidor");
        response.put("errorMsg", e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Map<String, Object>> tokenExpiredException(JwtException e) {
        log.error("Error: {}", e.getLocalizedMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Map<String, Object>> tokenExpiredException(ExpiredJwtException e) {
        log.error("Error: {}", e.getLocalizedMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Token expirado");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> entityViolations(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El correo ya esta registrado");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<Object> validationExceptions(TransactionSystemException ex){
        Map<String, Object> response = new HashMap<>();
        Throwable cause = ex.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            List<Object> errorList = new ArrayList<>();
            constraintViolations.forEach(x -> {
                Map<String, String> error = new HashMap<>();
                error.put("field", x.getPropertyPath().toString());
                error.put("message", x.getMessage());
                errorList.add(error);
            });
            response.put("message", "Error en validaciones de usuario");
            response.put("fields", errorList);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        log.error(ex.getMessage(), ex);
        response.put("error", "No se pudo categorizar el error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
