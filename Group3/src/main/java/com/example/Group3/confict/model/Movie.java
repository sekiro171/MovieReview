package com.example.Group3.confict.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title; // Tên phim
    private String genre; // Thể loại
    private String director; // Đạo diễn
    private int releaseYear; // Năm phát hành
    private String synopsis; // Tóm tắt nội dung
    private String coverImageUrl; // Link ảnh bìa
    private double averageRating = 0.0;

    public Movie(String title, String genre, String director, int releaseYear, String synopsis, String coverImageUrl,
            double averageRating) {
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.releaseYear = releaseYear;
        this.synopsis = synopsis;
        this.coverImageUrl = coverImageUrl;
        this.averageRating = averageRating;
    }

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

}
