package com.example.kurs.controller;

import com.example.kurs.Response;
import com.example.kurs.exceptions.IllegalKitchenException;
import com.example.kurs.exceptions.InvalidPageNumberException;
import com.example.kurs.exceptions.InvalidSortDirectionException;
import com.example.kurs.exceptions.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class UserControllerAdvice  {
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Response> handleNumberFormatException(NumberFormatException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPageNumberException.class)
    public ResponseEntity<Response> handleInvalidPageNumberException(InvalidPageNumberException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Response> handleRecipeNotFoundException(RecipeNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<Response> handleInvalidSortDirectionException(InvalidSortDirectionException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalKitchenException.class)
    public ResponseEntity<Response> handleIllegalKitchenException(IllegalKitchenException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
