package com.example.cinemaplanner.event.repository;

import com.example.cinemaplanner.event.model.Learning;
import com.example.cinemaplanner.event.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin on 30/09/2017 for ZKY.
 */
@Repository
public interface LearningRepository  extends JpaRepository<Learning, Long> {
    List<Learning> findByTeamId(int id);
}
