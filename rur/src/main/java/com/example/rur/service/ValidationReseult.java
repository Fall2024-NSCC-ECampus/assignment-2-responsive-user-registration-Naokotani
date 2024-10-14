package com.example.rur.service;

public class ValidationReseult {
    public String message;
    public boolean isValid;

    public ValidationReseult(String message, boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }
}
