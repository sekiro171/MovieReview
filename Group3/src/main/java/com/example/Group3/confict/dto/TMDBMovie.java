package com.example.Group3.confict.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class TMDBMovie {
    private Integer id;
    private String title;
    private String overview;
    
    @JsonProperty("poster_path")
    private String posterPath;
    
    @JsonProperty("backdrop_path")
    private String backdropPath;
    
    @JsonProperty("vote_average")
    private Double voteAverage;
    
    @JsonProperty("vote_count")
    private Integer voteCount;
    
    @JsonProperty("release_date")
    private String releaseDate;
    
    @JsonProperty("original_language")
    private String originalLanguage;
    
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    
    private Boolean adult;
    private Double popularity;
}