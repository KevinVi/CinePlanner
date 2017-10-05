package com.example.cinemaplanner.event.repository;

import com.example.cinemaplanner.event.model.learning.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kevin on 30/09/2017 for CinePlanner.
 */
public interface GenreRepository extends JpaRepository<Gender, Long> {
}
