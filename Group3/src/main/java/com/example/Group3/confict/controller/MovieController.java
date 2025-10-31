package com.example.Group3.confict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.service.MovieService;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie")
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/addMovie")
    public String showAddMovieForm() {
        return "addMovie";
    }

    @PostMapping("/movie/add")
    public String postMethodName(@RequestParam String title,
            @RequestParam String genre, String director, int releaseYear,
            String synopsis, String coverImageUrl,
            Model model) {
        movieService.addMovie(new Movie(title, genre, director, releaseYear, synopsis, coverImageUrl, 0.0));

        return "redirect:/movie";
    }

    @PostMapping("/movie/edit/{id}")
    public String editMovie(
            @PathVariable int id,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String synopsis,
            @RequestParam(required = false) String coverImageUrl,
            Model model) {

        movieService.updateMovie(id, title, genre, director, releaseYear, synopsis, coverImageUrl);
        return "redirect:/movie";
    }

}
