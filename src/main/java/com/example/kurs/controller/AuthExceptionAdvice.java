package com.example.kurs.controller;

import com.example.kurs.Response;
import com.example.kurs.exceptions.RecipeNotFoundException;
import com.example.kurs.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AuthExceptionAdvice {


    @ExceptionHandler( UsernameNotFoundException.class)
    public ResponseEntity<Response> handleUsernameNotFoundException(UsernameNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( AuthenticationException.class )
    public ResponseEntity<Response> handleAuthenticationException(AuthenticationException e) {
        Response response = new Response("Invalid username or password -> " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( UserAlreadyExistsException.class )
    public ResponseEntity<Response> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(409));
    }

    @ExceptionHandler( RecipeNotFoundException.class )
    public ResponseEntity<Response> handleRecipeNotFoundException(UserAlreadyExistsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
