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

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("testytoast has been registered successfully", validation.message);
        assert(validation.isValid);
    }

    @Test
    void badEmail() {
        String username = "testytoast";
        String email = "testtest.com";
        String password = "Tedddst12!";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("testtest.com is an invalid email", validation.message);
        assert(!validation.isValid);

    }

    @Test
    void shortUsername() {
        String username = "tes";
        String email = "test@test.com";
        String password = "Tedddst12!";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("tes is an invalid username", validation.message);
        assert(!validation.isValid);

    }

    @Test
    void spacesUsername() {
        String username = "te s";
        String email = "test@test.com";
        String password = "Tedddst12!";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assert(!validation.isValid);

    }

    @Test
    void noUpperPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst12!";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("Invalid password", validation.message);
        assert(!validation.isValid);

    }

    @Test
    void noDigitPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst!";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("Invalid password", validation.message);
        assert(!validation.isValid);

    }

    @Test
    void noSpecialPassword    () {
        String username = "testytest";
        String email = "test@test.com";
        String password = "tedddst12";

        ValidationReseult validation = new RegistrationImpl(userRepository).validateUser(username, email, password);

        System.out.println(validation.message);

        assertEquals("Invalid password", validation.message);
        assert(!validation.isValid);

    }
}