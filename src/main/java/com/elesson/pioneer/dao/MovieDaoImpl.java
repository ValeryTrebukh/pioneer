package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

    private SimpleCrudDao simpleDao = new SimpleCrudDaoImpl();

    private static MovieDao movieDao = null;

    private MovieDaoImpl() {}

    public static MovieDao getMovieDao() {
        if(movieDao ==null) {
            synchronized (MovieDaoImpl.class) {
                if(movieDao ==null) {
                    movieDao = new MovieDaoImpl();
                }
            }
        }
        if(logger.isDebugEnabled()) logger.debug("Admin movieDao received");
        return movieDao;
    }

    @Override
    public List<Movie> getAllMovies() {
        return getMovies("SELECT * FROM movies m");
    }

    @Override
    public List<Movie> getActiveMovies() {
        return getMovies("SELECT * FROM movies m WHERE active=true");
    }

    private List<Movie> getMovies(String query) {
        return simpleDao.getAllById(Movie.class, query);
    }

    @Override
    public Movie save(Movie movie) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst;
            if(movie.isNew()) {
                String query = "INSERT INTO movies (name, genre, duration, year, active) VALUES (?, ?, ?, ?, ?)";
                pst = con.prepareInsertStatement(query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive());
                if(pst.executeUpdate()==1) {
                    ResultSet rs = pst.getGeneratedKeys();
                    rs.next();
                    movie.setId(rs.getInt(1));
                    rs.close();
                }
                logger.info("New movie created with id={}", movie.getId());
            } else {
                String query = "UPDATE movies SET name=?, genre=?, duration=?, year=?, active=? WHERE mid=?";
                pst = con.prepareStatement(query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.isActive(), movie.getId());

                if(pst.executeUpdate()!=1) {
                    logger.error("Movie was not not created");
                    // TODO: throw new exception
                }
                logger.info("Movie data successfully updated for id=" + movie.getId());
            }
        } catch (SQLException e) {
            if(e.getMessage().contains("Duplicate")) {
                logger.error(e);
//                throw new DuplicateEntityException();
            }
            logger.error(e);
//            throw new DBException("Unable to save new record");
        }
        return movie;
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
}
