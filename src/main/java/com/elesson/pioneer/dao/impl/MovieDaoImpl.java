package com.elesson.pioneer.dao.impl;

import com.elesson.pioneer.dao.Dao;
import com.elesson.pioneer.dao.DaoStrategyFactory;
import com.elesson.pioneer.dao.MovieDao;
import com.elesson.pioneer.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.elesson.pioneer.dao.DaoStrategyFactory.getStrategy;

/**
 * This class provides implementation of all {@code MovieDao} interface methods.
 */
public class MovieDaoImpl implements MovieDao {

    private static final String SELECT_ALL_MOVIES = "SELECT * FROM movies";
    private static final String INSERT_MOVIE = "INSERT INTO movies (name, genre, duration, year, active) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_MOVIE = "UPDATE movies SET name=?, genre=?, duration=?, year=?, active=? WHERE mid=?";
    private static final String DELETE_MOVIE = "DELETE FROM movies WHERE mid=?";
    private static final String SELECT_MOVIE_BY_ID = "SELECT * FROM movies WHERE mid=?";

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);
    private Dao simpleDao = getStrategy(DaoStrategyFactory.Strategy.JDBC);

    private static volatile MovieDao dao;

    private MovieDaoImpl() {}

    public static MovieDao getDao() {
        if(dao ==null) {
            synchronized (MovieDaoImpl.class) {
                if(dao ==null) {
                    dao = new MovieDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("MovieDaoImpl received");
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getAll() {
        return simpleDao.getAll(Movie.class, SELECT_ALL_MOVIES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Movie save(Movie movie) {
        if(movie.isNew()) {
            return simpleDao.save(movie, INSERT_MOVIE, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive());
        } else {
            return simpleDao.update(movie, UPDATE_MOVIE, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive(), movie.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        return simpleDao.delete(DELETE_MOVIE, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Movie getById(int id) {
        return simpleDao.get(Movie.class, SELECT_MOVIE_BY_ID, id);
    }
}
