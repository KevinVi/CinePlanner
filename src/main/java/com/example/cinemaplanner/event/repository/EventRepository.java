package com.example.cinemaplanner.event.repository;

import com.example.cinemaplanner.event.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 13/08/2017 for CinePlanner.
 */

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    Event findById(int id);
}
