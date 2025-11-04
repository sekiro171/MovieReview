package com.example.Group3.confict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.repository.MovieRepository;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findMovieById(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public boolean addMovie(Movie movie) {
        if (movieRepository.existsById(movie.getId())) {
            return false;
        }
        movieRepository.save(movie);
        return true;
    }

    public boolean deleteMovie(int id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMovie(int id, String title, String genre, String director, int releaseYear,
            String synopsis, String coverImageUrl) {

        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setDirector(director);
        movie.setReleaseYear(releaseYear);
        movie.setSynopsis(synopsis);
        movie.setCoverImageUrl(coverImageUrl);
        movieRepository.save(movie);
        return true;

    }
    public List<Movie> searchMoviesByTitle(String keyword) {
                if (keyword == null || keyword.trim().isEmpty()) {
                    return movieRepository.findAll();
                }
                return movieRepository.findByTitleContainingIgnoreCase(keyword);
            }


}
