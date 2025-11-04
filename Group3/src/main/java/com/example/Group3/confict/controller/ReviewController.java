package com.example.Group3.confict.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.Group3.confict.model.Movie;
import com.example.Group3.confict.model.Review;
import com.example.Group3.confict.model.Users;
import com.example.Group3.confict.service.MovieService;
import com.example.Group3.confict.service.ReviewService;
import com.example.Group3.confict.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    // ---- Web page: list reviews (kept) ----
    @GetMapping("/reviews")
    public String listReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews",reviews);
        return "review";
    }

    // ---- REST endpoints for client JS ----

    // DTO to avoid exposing full entity (prevents JSON infinite recursion)
    public static class ReviewDTO {
        public int id;
        public int rating;
        public String comment;
        public Integer userId;
        public String userName;
        public int movieId;
        public String createdAt; // ISO string

        public ReviewDTO() {}

        public ReviewDTO(Review r) {
            this.id = r.getId();
            this.rating = r.getRating();
            this.comment = r.getComment();
            if (r.getUser() != null) {
                this.userId = r.getUser().getId();
                this.userName = r.getUser().getUserName();
            }
            if (r.getMovie() != null) {
                this.movieId = r.getMovie().getId();
            }
            try {
                if (r.getCreatedAt() != null) {
                    // adjust if createdAt is LocalDateTime
                    this.createdAt = r.getCreatedAt().toString();
                }
            } catch (Exception ex) {
                this.createdAt = null;
            }
        }
    }

    /**
     * Get reviews for a movie.
     * Optional query param 'sort':
     *   newest (default) -> id desc
     *   oldest -> id asc
     *   top -> rating desc
     *   low -> rating asc
     */
    @GetMapping("/api/reviews/movie/{movieId}")
    @ResponseBody
    public ResponseEntity<List<ReviewDTO>> getReviewsByMovie(
            @PathVariable int movieId,
            @RequestParam(name = "sort", required = false, defaultValue = "newest") String sort) {

        List<Review> reviews = reviewService.findByMovieId(movieId);
        if (reviews == null) reviews = new ArrayList<>();

        // sort
        switch (sort.toLowerCase()) {
            case "oldest":
                reviews = reviews.stream()
                        .sorted(Comparator.comparingInt(Review::getId))
                        .collect(Collectors.toList());
                break;
            case "top":
                reviews = reviews.stream()
                        .sorted(Comparator.comparingInt(Review::getRating).reversed())
                        .collect(Collectors.toList());
                break;
            case "low":
                reviews = reviews.stream()
                        .sorted(Comparator.comparingInt(Review::getRating))
                        .collect(Collectors.toList());
                break;
            case "newest":
            default:
                reviews = reviews.stream()
                        .sorted(Comparator.comparingInt(Review::getId).reversed())
                        .collect(Collectors.toList());
                break;
        }

        List<ReviewDTO> dtos = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Create a new review.
     * Required params: movieId, userId, rating
     * Optional: comment
     */
    @PostMapping("/api/reviews")
    @ResponseBody
    public ResponseEntity<?> createOrUpsertReview(
            @RequestParam int movieId,
            @RequestParam int userId,
            @RequestParam int rating,
            @RequestParam(required = false, defaultValue = "") String comment) {

        // rating: 0..10
        if (rating < 0 || rating > 10) {
            return ResponseEntity.badRequest().body("Rating phải nằm giữa 0 và 10");
        }

        Movie movie = movieService.findMovieById(movieId);
        if (movie == null) {
            return ResponseEntity.badRequest().body("Movie không tồn tại");
        }

        Users user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }

        // Kiểm tra xem user đã có review cho movie này chưa
        Review existing = null;
        List<Review> movieReviews = reviewService.findByMovieId(movieId);
        if (movieReviews != null) {
            for (Review r : movieReviews) {
                if (r.getUser() != null && r.getUser().getId() == userId) {
                    existing = r;
                    break;
                }
            }
        }

        if (existing != null) {
            // update existing
            existing.setRating(rating);
            existing.setComment(comment);
            Review saved = reviewService.updateReview(existing);
            return ResponseEntity.ok(new ReviewDTO(saved));
        } else {
            // create new
            Review review = new Review();
            review.setMovie(movie);
            review.setUser(user);
            review.setRating(rating);
            review.setComment(comment);
            Review saved = reviewService.saveReview(review);
            return ResponseEntity.ok(new ReviewDTO(saved));
        }
    }

    /**
     * Update an existing review: only rating/comment allowed.
     */
    @PutMapping("/api/reviews/{id}")
    @ResponseBody
    public ResponseEntity<?> updateReview(
            @PathVariable int id,
            @RequestParam int userId,
            @RequestParam int rating,
            @RequestParam(required = false, defaultValue = "") String comment) {

        if (rating < 0 || rating > 10) {
            return ResponseEntity.badRequest().body("Rating phải nằm giữa 0 và 10");
        }

        Optional<Review> orev = reviewService.findById(id);
        if (!orev.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Review existing = orev.get();
        // chỉ cho phép user chủ sở hữu sửa
        if (existing.getUser() == null || existing.getUser().getId() != userId) {
            return ResponseEntity.status(403).body("Không có quyền sửa bình luận này");
        }

        existing.setRating(rating);
        existing.setComment(comment);
        Review saved = reviewService.updateReview(existing);
        return ResponseEntity.ok(new ReviewDTO(saved));
    }

    /**
     * Delete review by id
     */
    @DeleteMapping("/api/reviews/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteReviewApi(
            @PathVariable int id,
            @RequestParam int userId) {

        Optional<Review> orev = reviewService.findById(id);
        if (!orev.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Review existing = orev.get();
        // chỉ cho phép user chủ sở hữu xóa
        if (existing.getUser() == null || existing.getUser().getId() != userId) {
            return ResponseEntity.status(403).body("Không có quyền xóa bình luận này");
        }

        reviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
