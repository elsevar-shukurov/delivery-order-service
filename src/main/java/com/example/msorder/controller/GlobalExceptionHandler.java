package com.example.msorder.controller;

import com.example.msorder.exceptions.CourierUnavailableException;
import com.example.msorder.exceptions.InvalidOrderStatusException;
import com.example.msorder.exceptions.OrderNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.http.HttpResponse;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler  {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleOrderNotFound(OrderNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidStatus(InvalidOrderStatusException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(CourierUnavailableException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleCourierUnavailable(CourierUnavailableException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse("Internal server error: " + ex.getMessage());
    }
}

