package com.example.kurs.controller;

import com.example.kurs.Response;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.SystemException;


@ControllerAdvice
public class GeneralPurposeExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Response> handleSystemException(Exception e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
