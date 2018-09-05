package com.elesson.pioneer.service.util;

import com.elesson.pioneer.model.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides local storage of list of movies.
 * Every modification operation (create, update, delete) clears the stored list.
 *
 */
public class MovieCache {
    private static volatile List<Movie> movies = new ArrayList<>();

    public static List<Movie> getMovies() {
        return movies;
    }

    public static void setMovies(List<Movie> movies) {
        MovieCache.movies = movies;
    }

    public static void invalidate() {
        movies.clear();
    }
}
