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

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;
    private final ReviewService reviewService;

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
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Users user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            if ("Admin".equals(userService.getRole(user))) {
                return "dashboard";
            } else {
                return "redirect:/home";
            }
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
            model.addAttribute("successMessage", "Registration successful. Please log in.");
            return "login";
        } else {
            model.addAttribute("errorMessage", "Registration failed. Please try again.");
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}