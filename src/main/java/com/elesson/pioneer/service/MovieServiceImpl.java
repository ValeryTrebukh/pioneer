package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.BaseDao;
import com.elesson.pioneer.dao.DaoFactory;
import com.elesson.pioneer.model.Movie;

import java.util.List;

import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;
import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFoundWithId;

/**
 * Provides implementation of all {@code MovieService} interface methods.
 */
public class MovieServiceImpl implements MovieService {

    private BaseDao movieDao;
    private static volatile MovieService service;

    private MovieServiceImpl() {
        movieDao = DaoFactory.getDao(DaoFactory.DaoType.MOVIE);
    }

    public static MovieService getMovieService() {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        checkNotFoundWithId(movieDao.delete(id), id);
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getAllMovies() {
        return movieDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getActiveMovies() {
        return movieDao.getActive();
    }
}
