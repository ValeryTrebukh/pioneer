package com.elesson.pioneer.service.util;

import com.elesson.pioneer.dao.DaoFactory;
import com.elesson.pioneer.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Provides local storage of list of movies.
 * Every modification operation (create, update, delete) clears the stored list.
 *
 */
public class MovieCache {
    private static volatile List<Movie> movies = new ArrayList<>();

    public static List<Movie> getMovies() {
        if(movies.isEmpty()) {
            synchronized (UserCache.class) {
                if(movies.isEmpty()) {
                    movies = DaoFactory.getDao(DaoFactory.DaoType.MOVIE).getAll();
                }
            }
        }
        return movies;
    }

    public static List<Movie> getActiveMovies() {
        return getMovies().stream().filter(Movie::isActive).collect(Collectors.toList());
    }

    public static synchronized void invalidate() {
        movies.clear();
    }
}
