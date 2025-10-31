package com.example.Group3.confict.controller;

import com.example.Group3.confict.dto.MovieDTO;
import com.example.Group3.confict.service.MovieService;
import com.example.Group3.confict.service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*") // Cho phép CORS nếu frontend chạy riêng
public class MovieController {
    
    private final MovieService movieService;
    private final TMDBService tmdbService;
    
    @Autowired
    public MovieController(MovieService movieService, TMDBService tmdbService) {
        this.movieService = movieService;
        this.tmdbService = tmdbService;
    }
    
    /**
     * GET /api/movies/popular - Danh sách phim hot
     */
    @GetMapping("/popular")
    public ResponseEntity<List<MovieDTO>> getPopularMovies() {
        List<MovieDTO> movies = movieService.getPopularMovies();
        return ResponseEntity.ok(movies);
    }
    
    /**
     * GET /api/movies/latest - Danh sách phim mới nhất
     */
    @GetMapping("/latest")
    public ResponseEntity<List<MovieDTO>> getLatestMovies() {
        List<MovieDTO> movies = movieService.getLatestMovies();
        return ResponseEntity.ok(movies);
    }
    
    /**
     * GET /api/movies/top-rated - Danh sách phim đánh giá cao
     */
    @GetMapping("/top-rated")
    public ResponseEntity<List<MovieDTO>> getTopRatedMovies() {
        List<MovieDTO> movies = movieService.getTopRatedMovies();
        return ResponseEntity.ok(movies);
    }
    
    /**
     * GET /api/movies/{id} - Chi tiết một phim
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieDetail(@PathVariable int id) {
        MovieDTO movie = movieService.getMovieDetail(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * GET /api/movies/homepage - Lấy tất cả data cho homepage một lần
     */
    @GetMapping("/homepage")
    public ResponseEntity<Map<String, List<MovieDTO>>> getHomepageData() {
        Map<String, List<MovieDTO>> data = new HashMap<>();
        
        data.put("popular", movieService.getPopularMovies());
        data.put("latest", movieService.getLatestMovies());
        data.put("topRated", movieService.getTopRatedMovies());
        
        return ResponseEntity.ok(data);
    }
    
    /**
     * POST /api/movies/sync - Đồng bộ phim từ TMDB ngay lập tức (manual trigger)
     */
    @PostMapping("/sync")
    public ResponseEntity<Map<String, String>> syncMovies() {
        try {
            tmdbService.fetchPopularMovies();
            tmdbService.fetchTopRatedMovies();
            tmdbService.fetchNowPlayingMovies();
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Movies synced successfully from TMDB");
            response.put("status", "success");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error syncing movies: " + e.getMessage());
            response.put("status", "error");
            
            return ResponseEntity.status(500).body(response);
        }
    }
}