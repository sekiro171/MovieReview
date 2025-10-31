package com.example.Group3.confict.service;

import com.example.Group3.confict.dto.TMDBMovie;
import com.example.Group3.confict.dto.TMDBMovieResponse;
import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.repository.MovieRepository;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TMDBService {
    
    private static final Logger logger = LoggerFactory.getLogger(TMDBService.class);
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";
    
    @Value("${tmdb.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;
    
    @Autowired
    public TMDBService(RestTemplate restTemplate, MovieRepository movieRepository) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }
    
    /**
     * Tự động fetch phim mỗi ngày lúc 3h sáng
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduledSync() {
        logger.info("Starting scheduled movie sync from TMDB...");
        fetchPopularMovies();
        fetchTopRatedMovies();
        fetchNowPlayingMovies();
        logger.info("Scheduled movie sync completed!");
    }
    
    /**
     * Fetch phim popular từ TMDB
     */
    public void fetchPopularMovies() {
        String url = String.format("%s/movie/popular?api_key=%s&language=vi-VN&page=1", 
                                    TMDB_BASE_URL, apiKey);
        fetchAndSaveMovies(url, "Popular");
    }
    
    /**
     * Fetch phim top rated từ TMDB
     */
    public void fetchTopRatedMovies() {
        String url = String.format("%s/movie/top_rated?api_key=%s&language=vi-VN&page=1", 
                                    TMDB_BASE_URL, apiKey);
        fetchAndSaveMovies(url, "Top Rated");
    }
    
    /**
     * Fetch phim now playing từ TMDB
     */
    public void fetchNowPlayingMovies() {
        String url = String.format("%s/movie/now_playing?api_key=%s&language=vi-VN&page=1", 
                                    TMDB_BASE_URL, apiKey);
        fetchAndSaveMovies(url, "Now Playing");
    }
    
    /**
     * Helper method để fetch và save movies
     */
    private void fetchAndSaveMovies(String url, String category) {
        try {
            logger.info("Fetching {} movies from TMDB...", category);
            
            TMDBMovieResponse response = restTemplate.getForObject(url, TMDBMovieResponse.class);
            
            if (response != null && response.getResults() != null) {
                List<TMDBMovie> tmdbMovies = response.getResults();
                logger.info("Found {} {} movies", tmdbMovies.size(), category);
                
                for (TMDBMovie tmdbMovie : tmdbMovies) {
                    saveOrUpdateMovie(tmdbMovie);
                }
                
                logger.info("Successfully saved {} movies", category);
            }
        } catch (Exception e) {
            logger.error("Error fetching {} movies: {}", category, e.getMessage(), e);
        }
    }
    
    /**
     * Lưu hoặc cập nhật movie vào database
     */
    private void saveOrUpdateMovie(TMDBMovie tmdbMovie) {
        try {
            Movie movie = movieRepository.findById(tmdbMovie.getId())
                    .orElse(new Movie());
            
            movie.setId(tmdbMovie.getId());
            movie.setTitle(tmdbMovie.getTitle());
            movie.setOverview(tmdbMovie.getOverview());
            movie.setPosterPath(tmdbMovie.getPosterPath());
            movie.setBackdropPath(tmdbMovie.getBackdropPath());
            movie.setVoteAverage(tmdbMovie.getVoteAverage());
            movie.setVoteCount(tmdbMovie.getVoteCount());
            movie.setPopularity(tmdbMovie.getPopularity());
            movie.setOriginalLanguage(tmdbMovie.getOriginalLanguage());
            movie.setAdult(tmdbMovie.getAdult());
            
            // Parse release date
            if (tmdbMovie.getReleaseDate() != null && !tmdbMovie.getReleaseDate().isEmpty()) {
                try {
                    LocalDate releaseDate = LocalDate.parse(tmdbMovie.getReleaseDate(), 
                                                           DateTimeFormatter.ISO_DATE);
                    movie.setReleaseDate(releaseDate);
                    movie.setReleaseYear(releaseDate.getYear());
                } catch (Exception e) {
                    logger.warn("Could not parse release date: {}", tmdbMovie.getReleaseDate());
                }
            }
            
            // Set average rating
            if (tmdbMovie.getVoteAverage() != null) {
                movie.setAverageRating(tmdbMovie.getVoteAverage());
            }
            
            movieRepository.save(movie);
            logger.debug("Saved movie: {} (ID: {})", movie.getTitle(), movie.getId());
            
        } catch (Exception e) {
            logger.error("Error saving movie {}: {}", tmdbMovie.getTitle(), e.getMessage());
        }
    }

    @PostConstruct
public void initializeMovies() {
    logger.info("Initializing movie database...");
    long movieCount = movieRepository.count();
    
    if (movieCount == 0) {
        logger.info("No movies found. Fetching from TMDB...");
        fetchPopularMovies();
        fetchTopRatedMovies();
        fetchNowPlayingMovies();
    } else {
        logger.info("Found {} movies in database", movieCount);
    }
}
}