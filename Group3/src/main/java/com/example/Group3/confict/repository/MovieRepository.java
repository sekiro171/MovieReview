package com.example.Group3.confict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Group3.confict.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
