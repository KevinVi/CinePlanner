package com.example.cinemaplanner.team.repository;

import com.example.cinemaplanner.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 06/08/2017 .
 */

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
}
