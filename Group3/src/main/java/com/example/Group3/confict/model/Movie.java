package com.example.Group3.confict.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private String title;
    private String genre;
    private String director;
    private int releaseYear;
    private String synopsis;
    private String coverImageUrl;
    private double averageRating = 0.0;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();

    public Movie(String title, String genre, String director, int releaseYear,
            String synopsis, String coverImageUrl, double averageRating) {
        this.title = title;
        this.genre = genre;
        this.director = director;
        this.releaseYear = releaseYear;
        this.synopsis = synopsis;
        this.coverImageUrl = coverImageUrl;
        this.averageRating = averageRating;
        this.reviews = new ArrayList<>();
    }
}