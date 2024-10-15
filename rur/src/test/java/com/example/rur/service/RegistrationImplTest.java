package com.example.rur.service;

import com.example.rur.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RegistrationImplTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void validateUser() {
        String username = "testytoast";
        String email = "test@test.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("testytoast has been registered successfully", validation.successMessage);
        assert(validation.isUsernameValid);
    }

    @Test
    void noAtEmail() {
        String username = "testytoast";
        String email = "testtest.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("testtest.com is an invalid email.", validation.invalidEmailMessage);
        assert(!validation.isEmailValid);
    }

    @Test
    void spacesEmail() {
        String username = "testytoast";
        String email = "test@te st.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("test@te st.com is an invalid email.", validation.invalidEmailMessage);
        assert(!validation.isEmailValid);
    }

    @Test
    void noDotEmail() {
        String username = "testytoast";
        String email = "test@testcom";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("test@testcom is an invalid email.", validation.invalidEmailMessage);
        assert(!validation.isEmailValid);
    }

    @Test
    void shortUsername() {
        String username = "tes";
        String email = "test@test.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("tes is an invalid username.", validation.invalidUsernameMessage);
        assert(!validation.isUsernameValid);
    }

    @Test
    void spacesUsername() {
        String username = "te sssasdfd";
        String email = "test@test.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assertEquals("te sssasdfd is an invalid username.", validation.invalidUsernameMessage);
        assert(!validation.isUsernameValid);
    }

    @Test
    void noUpperPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst12!";
        String confirmPassword = "tedddst12!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assert(!validation.isPasswordValid);

    }

    @Test
    void noDigitPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst!";
        String confirmPassword = "tedddst!";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assert(!validation.isPasswordValid);
    }

    @Test
    void noSpecialPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst12";
        String confirmPassword = "tedddst12";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assert(!validation.isPasswordValid);
    }

    @Test
    void noPasswordsMatch() {
        String username = "testytest";
        String email = "test@test.com";
        String password = "Tedddst12!";
        String confirmPassword = "Tedddst12@";
        ValidationResult validation = new RegistrationImpl(userRepository)
                .validateUser(username, email, password, confirmPassword);
        assert(!validation.passwordsMatch);
    }
}