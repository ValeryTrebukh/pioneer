package com.elesson.pioneer.service;

import com.elesson.pioneer.model.Movie;

import java.util.List;

public interface MovieService {

    boolean create(Movie movie);

    boolean delete(int id);

    Movie get(int id);

    void update(Movie movie);

    List<Movie> getAllMovies();

    List<Movie> getActiveMovies();
}
