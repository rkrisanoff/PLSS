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
@ControllerAdvice

public class UserControllerAdvice {

    @ExceptionHandler(InvalidPageNumberException.class)
    public ResponseEntity<Response> handleInvalidPageNumberException(IllegalKitchenException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Response> handleRecipeNotFoundException(RecipeNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPageNumberException.class)
    public ResponseEntity<Response> handleInvalidSizeException(IllegalKitchenException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<Response> handleInvalidSortDirectionException(IllegalKitchenException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
