package com.example.cinemaplanner.event.model;

import lombok.*;

import java.util.List;

/**
 * Created by Kevin on 30/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LearningBody {
    List<LearningContent> content;
}
