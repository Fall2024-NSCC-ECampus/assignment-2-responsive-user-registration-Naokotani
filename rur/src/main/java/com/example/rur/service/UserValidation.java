package com.example.rur.service;

public interface UserValidation {
   ValidationResult validateUser(String username, String email, String password);
}
