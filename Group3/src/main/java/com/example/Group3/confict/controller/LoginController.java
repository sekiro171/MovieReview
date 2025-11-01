package com.example.Group3.confict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Group3.confict.model.Users;
import com.example.Group3.confict.service.ReviewService;
import com.example.Group3.confict.service.UserService;

@Controller
public class LoginController {

    private final ReviewService reviewService;

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Users user = userService.login(username, password);
        if (userService.getRole(user).equals("Admin")) {
            return "dashboard";
        } else if (userService.getRole(user).equals("User")) {
            return "index";
        }
        return "error";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (userService.register(username, password)) {
            String successMessage = "Registration successful. Please log in.";
            model.addAttribute("successMessage", successMessage);
            return "login";
        } else {
            String errorMessage = "Registration failed. Please try again.";
            model.addAttribute("errorMessage", errorMessage);
            return "register";
        }

    }

}