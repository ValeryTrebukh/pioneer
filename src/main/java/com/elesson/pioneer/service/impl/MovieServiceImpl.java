package com.elesson.pioneer.service.impl;

import com.elesson.pioneer.dao.DaoEntityFactory;
import com.elesson.pioneer.dao.MovieDao;
import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.util.MovieCache;

import java.util.List;
import java.util.stream.Collectors;

import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;
import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFoundWithId;

/**
 * Provides implementation of all {@code MovieService} interface methods.
 * All modification operations invalidate cached collection of movies.
 */
public class MovieServiceImpl implements MovieService {

    private MovieDao movieDao;
    private static volatile MovieService service;

    private MovieServiceImpl() {
        movieDao = DaoEntityFactory.getMovieDao();
    }

    public static MovieService getService() {
        if(service ==null) {
            synchronized (MovieServiceImpl.class) {
                if(service ==null) {
                    service = new MovieServiceImpl();
                }
            }
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(Movie movie) {
        checkNotFound(movie, "movie must not be null");
        checkNotFound(movieDao.save(movie), "movie must not be null");
        MovieCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        checkNotFoundWithId(movieDao.delete(id), id);
        MovieCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Movie get(int id) {
        return checkNotFoundWithId(movieDao.getById(id), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Movie movie) {
        checkNotFound(movie, "movie must not be null");
        checkNotFoundWithId(movieDao.save(movie), movie.getId());
        MovieCache.invalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getAllMovies() {
        if(MovieCache.getMovies().isEmpty()) {
            MovieCache.setMovies(movieDao.getAll());
        }
        return MovieCache.getMovies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getActiveMovies() {
        if(MovieCache.getMovies().isEmpty()) {
            MovieCache.setMovies(movieDao.getAll());
        }
        return MovieCache.getMovies().stream().filter(Movie::isActive).collect(Collectors.toList());
    }
}
