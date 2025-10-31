package com.example.Group3.confict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.model.Review;
import com.example.Group3.confict.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean addReview(Movie movie) {
        if (reviewRepository.existsById(movie.getId())) {
            return false;
        }
        reviewRepository.save(movie);
        return true;
    }

    public boolean deleteById(int id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;

        }
    }

    public boolean updateReview(Movie movie) {
        if (reviewRepository.existsById(movie.getId())) {
            reviewRepository.save(movie);
            return true;
        } else {
            return false;
        }
    }

    public Review findReviewById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }
}