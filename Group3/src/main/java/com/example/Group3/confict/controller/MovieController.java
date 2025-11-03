package com.example.Group3.confict.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.model.Review;
import com.example.Group3.confict.service.MovieService;

import jakarta.servlet.http.HttpSession;

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
    public String addMovie(@RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam(required = false) String director,
                           @RequestParam(required = false) Integer releaseYear,
                           @RequestParam(required = false) String synopsis,
                           @RequestParam(required = false) String coverImageUrl) {
        movieService.addMovie(new Movie(title, genre, director, releaseYear, synopsis, coverImageUrl, 0.0));
        return "redirect:/movie";
    }

    @PostMapping("/movie/edit/{id}")
    public String editMovie(@PathVariable int id,
                            @RequestParam String title,
                            @RequestParam String genre,
                            @RequestParam(required = false) String director,
                            @RequestParam(required = false) Integer releaseYear,
                            @RequestParam(required = false) String synopsis,
                            @RequestParam(required = false) String coverImageUrl) {
        movieService.updateMovie(id, title, genre, director, releaseYear, synopsis, coverImageUrl);
        return "redirect:/movie";
    }

    @DeleteMapping("/movie/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        if (movieService.deleteMovie(id)) {
            return ResponseEntity.ok("Movie deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }
    }

    @GetMapping("/movie/detail/{id}")
    public String getMovieDetail(@PathVariable int id, Model model, HttpSession session) {
        Movie movie = movieService.findMovieById(id);
        if (movie == null) {
            return "redirect:/";
        }

        model.addAttribute("movie", movie);
        List<Review> reviews = movie.getReviews();
        model.addAttribute("reviews", reviews != null ? reviews : new ArrayList<>());
        model.addAttribute("user", session.getAttribute("user"));

        return "movieDetail";
    }
}