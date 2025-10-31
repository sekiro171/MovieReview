package com.example.Group3.confict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    /**
     * Trang chủ - hiển thị homepage
     */
    @GetMapping("/")
    public String home() {
        return "index"; // Trả về templates/index.html
    }
    
    @GetMapping("/movies")
    public String moviesPage() {
        return "movies"; // Trả về templates/movies.html
    }
}