package com.example.rur.controller;

import com.example.rur.model.User;
import com.example.rur.repository.UserRepository;
import com.example.rur.service.Registration;
import com.example.rur.service.RegistrationImpl;
import com.example.rur.service.ValidationReseult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationForm {

    final
    UserRepository userRepository;

    public RegistrationForm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user, Model model) {
        Registration registration = new RegistrationImpl(userRepository);
        ValidationReseult validationReseult = registration.validateUser(user.getUsername(),
                user.getEmail(), user.getPassword());
        model.addAttribute("message", validationReseult.message);
        return "result";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("message", "Hello!");
        return "register";
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

}
