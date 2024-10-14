package com.example.rur.service;

public interface Registration {
   ValidationResult validateUser(String username, String email, String password);
}
