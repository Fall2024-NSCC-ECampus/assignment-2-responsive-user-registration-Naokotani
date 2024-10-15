package com.example.rur.service;

public class ValidationResult {
    public boolean isEmailValid;
    public boolean isPasswordValid;
    public boolean isUsernameValid;
    public boolean isUserExists;
    public boolean isEmailExists;
    public boolean passwordsMatch;
    public static final String passwordsNotMatchMessage = "Passwords do not match";
    public String invalidEmailMessage = "";
    public static final String invalidPasswordMessage = "Invalid Password: Must contain at least 8 characters," +
            " one upper case adn one special character";
    public String invalidUsernameMessage = "";
    public String successMessage = "";

    public ValidationResult() {
        this.passwordsMatch = true;
        this.isUsernameValid = true;
        this.isEmailValid = true;
        this.isPasswordValid = true;
        this.isUserExists = false;
    }

    public boolean isInputValid() {
        return isUsernameValid &&
                isEmailValid &&
                isPasswordValid &&
                passwordsMatch &&
                !isUserExists &&
                !isEmailExists;
    }

}
