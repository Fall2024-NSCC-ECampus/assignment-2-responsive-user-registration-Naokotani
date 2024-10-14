package com.example.rur.repository;

import com.example.rur.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = ?1")
    Optional<User> emailExists(String emailAddress);

    @Query("select u from User u where u.username = ?1")
    Optional<User> usernameExists(String username);
}