package com.elesson.pioneer.service;

import com.elesson.pioneer.model.Movie;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie);

    void delete(int id);

    Movie get(int id);

    void update(Movie movie);

    List<Movie> getMovies();
}
