package com.example.cinemaplanner.group.repository;

import com.example.cinemaplanner.group.model.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kevin on 06/08/2017 .
 */

@Repository
public interface GroupRepository extends JpaRepository<GroupModel, String> {
}
