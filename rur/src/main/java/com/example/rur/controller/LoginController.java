package com.example.rur.controller;

import com.example.rur.model.User;
import com.example.rur.repository.UserRepository;
import com.example.rur.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    final
    UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user, Model model) {
        UserValidation userValidation = new UserValidationImpl(userRepository);
        ValidationResult validationResult = userValidation.validateUser(user.getUsername(),
                user.getEmail(), user.getPassword());
        if(!validationResult.isInputValid()) {
            model.addAttribute("v", validationResult);
            return "register";
        } else {
            UserRegistration userRegistration = new UserRegistrationImpl(userRepository);
            userRegistration.registerUser(validationResult);
            model.addAttribute("v", validationResult);
            return "welcome";
        }
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("v", new ValidationResult(new User()));
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
