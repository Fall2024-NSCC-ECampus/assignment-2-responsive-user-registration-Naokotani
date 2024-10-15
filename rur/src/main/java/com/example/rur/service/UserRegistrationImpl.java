package com.example.rur.service;

import com.example.rur.repository.UserRepository;

public class UserRegistrationImpl implements UserRegistration {
    private final UserRepository userRepository;

    public UserRegistrationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(ValidationResult validationResult) {
        validationResult.user.encodePassword();
        if(validationResult.user.getPassword().startsWith("{bcrypt}")) {
            saveUser(validationResult);
        } else {
            throw new RuntimeException("Invalid password hash");
        }
    }

    private void saveUser(ValidationResult validationResult) {
        try{
            userRepository.save(validationResult.user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            validationResult.successMessage =
                    String.format("%s has been registered successfully",
                            validationResult.user.getUsername());
        }

    }
}
