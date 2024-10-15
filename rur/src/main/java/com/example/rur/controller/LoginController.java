package com.example.rur.controller;

import com.example.rur.model.User;
import com.example.rur.repository.UserRepository;
import com.example.rur.service.Registration;
import com.example.rur.service.RegistrationImpl;
import com.example.rur.service.ValidationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    final
    UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String submitRegistration(@ModelAttribute User user,
                                     @RequestParam(name="confirm-passwordy") String confirmPassword,
                                     Model model) {
        Registration registration = new RegistrationImpl(userRepository);
        ValidationResult validationResult = registration.validateUser(user.getUsername(),
                user.getEmail(), user.getPassword(), confirmPassword);
        if(!validationResult.isInputValid()) {
            model.addAttribute("v", validationResult);
            return "register";
        } else {
            model.addAttribute("v", validationResult);
            return "welcome";
        }
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("v", new ValidationResult());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
