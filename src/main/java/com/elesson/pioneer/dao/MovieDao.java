package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();

    List<Movie> getActiveMovies();

    Movie save(Movie movie);

    boolean delete(int id);

    Movie getById(int id);
}
