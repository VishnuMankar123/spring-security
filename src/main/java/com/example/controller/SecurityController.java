package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SecurityController {


    @GetMapping("/")
    public String homePage() {
        return "home"; // login.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }


    @GetMapping("/home")
    public String home() {
        return "home"; // home.html
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome.html"; // home.html
    }
}
