package com.example.Group3.confict.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MovieDTO {
    private Integer id;
    private String title;
    private String overview;
    private String posterUrl; // Full URL
    private String backdropUrl; // Full URL
    private Double voteAverage;
    private LocalDate releaseDate;
    private Integer releaseYear;
    private String genre;
    private Double averageRating;
}