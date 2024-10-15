package com.example.rur.service;
import com.example.rur.model.User;
import com.example.rur.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.regex.Pattern;

public class UserValidationImpl implements UserValidation {
    private static final String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    private static final String userRegex = "^[A-Za-z]\\w{5,29}$";
    private final UserRepository userRepository;

    public UserValidationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ValidationResult validateUser(String username, String email, String password) {
        User user = new User(username, email, password);
        ValidationResult validation = validateInput(user);
        validation = checkUsernameEmailExists(validation);
        return validation;
    }

    private ValidationResult validateInput(User user) {
        ValidationResult validation = new ValidationResult(user);
        validateUsername(user.getUsername(), validation);
        validateEmail(user.getEmail(), validation);
        validatePassword(user.getPassword(), validation);
        return validation;
    }

    private void validateEmail(String email, ValidationResult validation) {
        if(!Pattern.compile(emailRegex).matcher(email).matches()) {
            validation.invalidEmailMessage = String.format("%s is an invalid email.", email);
            validation.isEmailValid = false;
        }
    }

    private void validatePassword(String password,  ValidationResult validation) {
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

    private ValidationResult checkUsernameEmailExists( ValidationResult validation) {
        Optional<User> user = userRepository.emailExists(validation.user.getEmail());
        if (user.isPresent()) {
            validation.invalidUsernameMessage = "Email already exists";
            validation.isUserExists = true;
        }
        return usernameExists(validation);
    }

    private ValidationResult usernameExists(ValidationResult validation) {
        Optional<User> user = userRepository.usernameExists(validation.user.getUsername());
        if (user.isPresent()) {
            validation.invalidUsernameMessage = "Username already exists";
            validation.isUserExists = true;
        }
        return validation;
    }
}