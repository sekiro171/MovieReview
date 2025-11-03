package com.example.Group3.confict.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    void save(Movie movie);
    List<Review> findByMovieId(int movieId);
    List<Review> findByUserId(int userId);

}
