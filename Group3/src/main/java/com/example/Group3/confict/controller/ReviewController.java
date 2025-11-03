package com.example.Group3.confict.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.Group3.confict.model.Review;
import com.example.Group3.confict.service.ReviewService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews",reviews);
        return "review";
    }

    @DeleteMapping("/review/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int id){
     
         if (!reviewService.findById(id).isPresent()){
            return ResponseEntity.notFound().build();
         }
         
         reviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    
    
}
