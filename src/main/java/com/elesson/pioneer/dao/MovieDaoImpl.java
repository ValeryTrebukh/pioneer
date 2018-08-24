package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Entity;
import com.elesson.pioneer.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class MovieDaoImpl implements BaseDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static BaseDao movieDao = null;

    private MovieDaoImpl() {}

    public static BaseDao getMovieDao() {
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

    @Override
    public List<Movie> getAll() {
        return getMovies("SELECT * FROM movies m");
    }

    @Override
    public List<Movie> getActive() {
        return getMovies("SELECT * FROM movies m WHERE active=true");
    }

    private List<Movie> getMovies(String query) {
        return simpleDao.getAllById(Movie.class, query);
    }

    @Override
    public boolean save(Entity entity) {
        Movie movie = (Movie)entity;
        if(movie.isNew()) {
            String query = "INSERT INTO movies (name, genre, duration, year, active) VALUES (?, ?, ?, ?, ?)";
            return simpleDao.save(movie, query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive());
        } else {
            String query = "UPDATE movies SET name=?, genre=?, duration=?, year=?, active=? WHERE mid=?";
            return simpleDao.update(movie, query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive(), movie.getId());
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM movies WHERE mid=?;";
        return simpleDao.delete(query, id);
    }

    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM movies m WHERE mid=?";
        return simpleDao.getById(Movie.class, query, id);
    }


    @Override
    public List<Movie> getAllByDate(LocalDate date) {
        return null;
    }

    @Override
    public Movie getByValue(String email) {
        return null;
    }
}
