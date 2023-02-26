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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


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
    public ResponseEntity<Response> handleEUserAlreadyExistsException(UserAlreadyExistsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(409));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuffer responseString = new StringBuffer("");
        e.getBindingResult().getFieldErrors().stream().map(error ->error.getDefaultMessage()+", ").forEach(error->responseString.append(error));
        Response response = new Response(responseString.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
