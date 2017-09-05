package com.example.cinemaplanner.comment.repository;

import com.example.cinemaplanner.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 05/09/2017 for ZKY.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}