package com.example.Group3.confict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.Group3.confict.model.Movie;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    
    // Tìm phim hot theo popularity
    List<Movie> findTop20ByOrderByPopularityDesc();
    
    // Tìm phim mới nhất theo release date
    List<Movie> findTop20ByOrderByReleaseDateDesc();
    
    // Tìm phim theo vote average cao
    List<Movie> findTop20ByOrderByVoteAverageDesc();
    
    // Tìm phim theo genre
    List<Movie> findByGenreContainingIgnoreCase(String genre);
    
    // Kiểm tra phim đã tồn tại chưa
    boolean existsById(Integer id);
}