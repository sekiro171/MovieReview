package com.example.Group3.confict.model;

import java.util.List;

import jakarta.persistence.Column;
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

    @Column(columnDefinition = "NVARCHAR(255)")
    private String title;
    
    @Column(columnDefinition = "NVARCHAR(255)")
    private String genre;
    
    @Column(columnDefinition = "NVARCHAR(255)")
    private String director;
    
    private int releaseYear;
    
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String synopsis;
    
    @Column(columnDefinition = "NVARCHAR(500)")
    private String coverImageUrl;
    
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