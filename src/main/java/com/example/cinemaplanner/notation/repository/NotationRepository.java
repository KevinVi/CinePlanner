package com.example.cinemaplanner.notation.repository;

import com.example.cinemaplanner.comment.model.Comment;
import com.example.cinemaplanner.notation.model.Notation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 01/10/2017 for ZKY.
 */
@Repository
public interface NotationRepository extends JpaRepository<Notation, String> {
}
