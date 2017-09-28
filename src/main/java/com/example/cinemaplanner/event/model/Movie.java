package com.example.cinemaplanner.event.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 24/09/2017 for ZKY.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOVIE")
public class Movie {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "vote_count")
    float vote_count;

    @Column(name = "vote_average")
    float vote_average;

    @Column(name = "title")
    String title;

    @Column(name = "popularity")
    float popularity;

    @Column(name = "poster_path")
    String poster_path;

    @Column(name = "original_language")
    String original_language;

    @Column(name = "original_title")
    String original_title;

    @Column(name = "backdrop_path")
    String backdrop_path;

    @Column(name = "overview", length = 3000)
    String overview;

    @Column(name = "release_date")
    String release_date;

    @ElementCollection
    @Column(name = "gender")
    List<Integer> genre_ids;

    public Movie(JsonSearchResult result) {
        this.id = result.getId();
        this.vote_count = result.getVote_count();
        this.vote_average = result.getVote_average();
        this.title = result.getTitle();
        this.popularity = result.getPopularity();
        this.poster_path = result.getPoster_path();
        this.original_language = result.getOriginal_language();
        this.original_title = result.getOriginal_title();
        this.backdrop_path = result.getBackdrop_path();
        this.overview = result.getOverview();
        this.release_date = result.getRelease_date();
        List<Integer> genre = new ArrayList<>();
        for (JsonSearchGenre js :
                result.getGenre_ids()) {
            genre.add(js.getGenreId());
        }
        this.genre_ids = genre;
    }
}
