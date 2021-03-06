package com.example.cinemaplanner.event.model.learning;

import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 30/09/2017 for CinePlanner.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningBody {
    List<LearningContent> content;
}
