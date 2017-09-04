package com.example.cinemaplanner.team.repository;

import com.example.cinemaplanner.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kevin on 06/08/2017 .
 */

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    @Query("From Team t where :login member t.pendingUsers")
    List<Team> findByLogin(@Param("login") String login);

    Team findById(int id);
}
