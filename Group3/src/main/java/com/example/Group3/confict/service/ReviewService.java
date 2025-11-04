package com.example.Group3.confict.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.model.Review;
import com.example.Group3.confict.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAll(){
            return reviewRepository.findAll();  
    }

    public Optional<Review> findById(int id){
        return reviewRepository.findById(id);
    }

    public void deleteById(int id){
        reviewRepository.deleteById(id);
    }

    public Review saveReview(Review review){
        return reviewRepository.save(review);
    }

    public List<Review> findByMovieId(int movieId){
        return reviewRepository.findByMovieId(movieId);
    }

    public List<Review> findByUserId(int userId){
        return reviewRepository.findByUserId(userId);
    }

}