package com.example.Group3.confict.service;

import com.example.Group3.confict.dto.MovieDTO;
import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    
    private static final String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String TMDB_BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/original";
    
    private final MovieRepository movieRepository;
    
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    /**
     * Lấy danh sách phim popular
     */
    public List<MovieDTO> getPopularMovies() {
        List<Movie> movies = movieRepository.findTop20ByOrderByPopularityDesc();
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy danh sách phim mới nhất
     */
    public List<MovieDTO> getLatestMovies() {
        List<Movie> movies = movieRepository.findTop20ByOrderByReleaseDateDesc();
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy danh sách phim top rated
     */
    public List<MovieDTO> getTopRatedMovies() {
        List<Movie> movies = movieRepository.findTop20ByOrderByVoteAverageDesc();
        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Lấy chi tiết một phim
     */
    public MovieDTO getMovieDetail(int id) {
        return movieRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    
    /**
     * Convert Movie entity sang MovieDTO
     */
    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setOverview(movie.getOverview());
        
        // Build full URL cho poster và backdrop
        if (movie.getPosterPath() != null) {
            dto.setPosterUrl(TMDB_IMAGE_BASE_URL + movie.getPosterPath());
        }
        
        if (movie.getBackdropPath() != null) {
            dto.setBackdropUrl(TMDB_BACKDROP_BASE_URL + movie.getBackdropPath());
        }
        
        dto.setVoteAverage(movie.getVoteAverage());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setGenre(movie.getGenre());
        dto.setAverageRating(movie.getAverageRating());
        
        return dto;
    }
}