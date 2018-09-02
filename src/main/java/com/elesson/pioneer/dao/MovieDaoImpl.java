package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.elesson.pioneer.dao.DaoStrategyFactory.getStrategy;

/**
 * This class provides implementation of all {@code MovieDao} interface methods.
 */
public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);
    private Dao simpleDao = getStrategy(DaoStrategyFactory.Strategy.JDBC);

    private static volatile MovieDaoImpl movieDao;

    private MovieDaoImpl() {}

    public static MovieDaoImpl getMovieDao() {
        if(movieDao ==null) {
            synchronized (MovieDaoImpl.class) {
                if(movieDao ==null) {
                    movieDao = new MovieDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("MovieDaoImpl received");
        return movieDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Movie> getAll() {
        String query = "SELECT * FROM movies";
        return simpleDao.getAll(Movie.class, query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Movie save(Movie movie) {
        if(movie.isNew()) {
            String query = "INSERT INTO movies (name, genre, duration, year, active) VALUES (?, ?, ?, ?, ?)";
            return simpleDao.save(movie, query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive());
        } else {
            String query = "UPDATE movies SET name=?, genre=?, duration=?, year=?, active=? WHERE mid=?";
            return simpleDao.update(movie, query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive(), movie.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM movies WHERE mid=?";
        return simpleDao.delete(query, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM movies WHERE mid=?";
        return simpleDao.get(Movie.class, query, id);
    }
}
