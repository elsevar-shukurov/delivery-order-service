package com.example.msorder.exceptions;

public class CourierUnavailableException extends RuntimeException {
    public CourierUnavailableException() {
        super("No available courier at the moment");
    }
}
