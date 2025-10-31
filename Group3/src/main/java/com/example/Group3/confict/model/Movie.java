package com.example.Group3.confict.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private Integer id; // Sử dụng ID từ TMDB thay vì auto-generate

    private String title;
    
    @Column(length = 2000)
    private String overview; // Mô tả phim
    
    private String posterPath; // Đường dẫn poster (ví dụ: /abc.jpg)
    private String backdropPath; // Đường dẫn backdrop cho banner
    
    private Double voteAverage; // Điểm đánh giá từ TMDB
    private Integer voteCount;
    
    private LocalDate releaseDate; // Ngày phát hành
    
    private String originalLanguage;
    private Boolean adult;
    private Double popularity;
    
    // Các trường cũ giữ lại để tương thích
    private String genre;
    private String director;
    private Integer releaseYear;
    private String synopsis;
    private String coverImageUrl;
    private Double averageRating = 0.0;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;

    // Constructor cho TMDB data
    public Movie(Integer id, String title, String overview, String posterPath, 
                 String backdropPath, Double voteAverage, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.averageRating = voteAverage != null ? voteAverage : 0.0;
    }
}