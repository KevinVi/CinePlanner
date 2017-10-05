package com.example.cinemaplanner.event.repository;

import com.example.cinemaplanner.event.model.learning.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 24/09/2017 for CinePlanner.
 */
@Repository
public interface SearchRepository extends JpaRepository<Movie, String> {
    Movie findById(int id);
}
