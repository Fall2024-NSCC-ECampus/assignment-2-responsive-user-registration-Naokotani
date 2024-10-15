package com.example.rur.service;

import com.example.rur.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserValidationImplTest {

    @Autowired
    UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {"testtest.com", "test@te st.com", "test@testcom"})
    void email(String email) {
        String username = "testytoast";
        String password = "Tedddst12!";
        ValidationResult validation = new UserValidationImpl(userRepository)
                .validateUser(username, email, password);
        assertEquals(email + " is an invalid email.", validation.invalidEmailMessage);
        assert(!validation.isEmailValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"tes, te sssasdfd"})
    void username(String username) {
        String email = "test@test.com";
        String password = "Tedddst12!";
        ValidationResult validation = new UserValidationImpl(userRepository)
                .validateUser(username, email, password);
        assertEquals(username + " is an invalid username.", validation.invalidUsernameMessage);
        assert(!validation.isUsernameValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {"tedddst12!", "tedddst!", "tedddst12"})
    void password(String password) {
        String username = "testytest";
        String email = "test@test.com";
        ValidationResult validation = new UserValidationImpl(userRepository)
                .validateUser(username, email, password);
        assert(!validation.isPasswordValid);
    }
}