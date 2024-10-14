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
    public ValidationReseult validateUser(String username, String email, String password) {
        if (!validateEmail(email)) {
            return new ValidationReseult(String.format("%s is an invalid email", email), false);
        } else if (!validatePassword(password)) {
            return new ValidationReseult(("Invalid password"), false);
        } else if (!validateUsername(username)) {
            return new ValidationReseult(String.format("%s is an invalid username", username), false);
        }

        User user = new User(username, email, password);

        ValidationReseult validation = detailsExists(user);

        if(validation.isValid) {
            try{
                userRepository.save(user);
            } catch (Exception e) {
                validation.message = "Failed to save user";
                throw new RuntimeException(e);
            } finally {
                validation.message = String.format("%s has been registered successfully", username);
            }
        }

        return validation;
    }

    private boolean validateEmail(String email) {
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    private boolean validatePassword(String password) {
        return Pattern.compile(passwordRegex).matcher(password).matches();
    }

    private boolean validateUsername(String username) {
        return Pattern.compile(userRegex).matcher(username).matches();
    }

    private ValidationReseult detailsExists(User user) {
        ValidationReseult validation = new ValidationReseult("", true);
        validation = emailExists(user.getEmail(), validation);
        validation = usernameExists(user.getUsername(), validation);
        validation.message = validation.message.trim();
        return validation;
    }

    private ValidationReseult emailExists(String email, ValidationReseult validation) {
        Optional<User> user = userRepository.emailExists(email);
        if (user.isPresent()) {
            validation.message = validation.message.concat("Email already exists");
            validation.isValid = false;
        }
        return validation;
    }

    private ValidationReseult usernameExists(String username, ValidationReseult validation) {
        Optional<User> user = userRepository.usernameExists(username);
        if (user.isPresent()) {
            validation.message = validation.message.concat(" username already exists");
            validation.isValid = false;
        }
        return validation;
    }
}