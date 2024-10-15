package com.example.rur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecretController {

    @GetMapping("/secret")
    public String secret() {
        return "secret";
    }

}
