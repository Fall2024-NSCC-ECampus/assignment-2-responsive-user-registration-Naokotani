package com.example.rur.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="users")
public class User {
    private static final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)" +
            "(?=.*[@#$%^&+=!]).{8,}$";
    private static final String passwordMessage = "Password must be at least" +
            " 8 characters and contain one upper case, one special character," +
            " one lower case and one digit";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message="Username must not be blank")
    @Size(min = 5, max = 20, message="Password must be between 5 and 20 characters")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Email(message="Must provide a valid email address")
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = passwordRegex, message=passwordMessage)
    @Column(name="password", nullable = false)
    @NotBlank(message="Password cannot be blank")
    private String password;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void encodePassword() {
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
