package com.example.rur.service;

public class ValidationResult {
    public boolean isEmailValid;
    public boolean isPasswordValid;
    public boolean isUsernameValid;
    public boolean isUserExists;
    public boolean isEmailExists;
    public String invalidEmailMessage = "";
    public static final String invalidPasswordMessage = "Invalid Password: Must contain at least 8 characters," +
            " one upper case adn one special character";
    public static final String errorMessage = "Failed to register user";
    public String invalidUsernameMessage = "";
    public String successMessage = "";

    public ValidationResult() {
        this.isUsernameValid = true;
        this.isEmailValid = true;
        this.isPasswordValid = true;
        this.isUserExists = false;
    }

    public boolean isInputValid() {
        return isUsernameValid && isEmailValid && isPasswordValid && !isUserExists && !isEmailExists;
    }

}
