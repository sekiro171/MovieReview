package com.example.Group3.confict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.service.MovieService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Movie> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("user", session.getAttribute("user"));
        return "home";
    }

    @GetMapping("/home")
    public String homepage(Model model, HttpSession session) {
        List<Movie> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("user", session.getAttribute("user"));
        return "home";
    }
    @GetMapping("/search")
    public String searchMovies(String keyword, Model model) {
        List<Movie> movies;
        if (keyword == null || keyword.trim().isEmpty()) {
            movies = movieService.findAllMovies();
        } else {
            movies = movieService.searchMoviesByTitle(keyword);
        }
        model.addAttribute("movies", movies);
        model.addAttribute("searchKeyword", keyword);
        return "home";
    }

}