package com.example.rur.service;
import com.example.rur.model.User;
import com.example.rur.repository.UserRepository;

import java.util.Optional;
import java.util.regex.Pattern;

public class RegistrationImpl implements Registration {
    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    private static final String userRegex = "^[A-Za-z]\\w{5,29}$";
    private final UserRepository userRepository;

    public RegistrationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ValidationResult validateUser(String username, String email, String password) {
        ValidationResult validation = validateInput(username, email, password);

        if(validation.isEmailValid && validation.isPasswordValid && validation.isUsernameValid) {
            User user = new User(username, email, password);
            validation = checkUsernameEmailExists(user.getEmail(), user.getUsername(), validation);
            try{
                userRepository.save(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                validation.successMessage = String.format("%s has been registered successfully", username);
            }
        }
        return validation;
    }

    private ValidationResult validateInput(String username, String email, String password) {
        ValidationResult validation = new ValidationResult();
        validateUsername(username, validation);
        validateEmail(email, validation);
        validatePassword(password, validation);
        return validation;
    }

    private void validateEmail(String email, ValidationResult validation) {
        if(!Pattern.compile(emailRegex).matcher(email).matches()) {
            validation.invalidEmailMessage = String.format("%s is an invalid email.", email);
            validation.isEmailValid = false;
        }
    }

    private void validatePassword(String password, ValidationResult validation) {
        if(!Pattern.compile(passwordRegex).matcher(password).matches()) {
           validation.isPasswordValid = false;
        }
    }

    private void validateUsername(String username, ValidationResult validation) {
        if(!Pattern.compile(userRegex).matcher(username).matches()) {
            validation.invalidUsernameMessage = String.format("%s is an invalid username.", username);
            validation.isUsernameValid = false;
        }
    }

    private ValidationResult checkUsernameEmailExists(String email, String username,
                                                       ValidationResult validation) {
        Optional<User> user = userRepository.emailExists(email);
        if (user.isPresent()) {
            validation.invalidUsernameMessage = "Email already exists";
            validation.isUserExists = true;
        }
        return usernameExists(username, validation);
    }

    private ValidationResult usernameExists(String username, ValidationResult validation) {
        Optional<User> user = userRepository.usernameExists(username);
        if (user.isPresent()) {
            validation.invalidUsernameMessage = "Username already exists";
            validation.isUserExists = true;
        }
        return validation;
    }
}