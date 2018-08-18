package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.ConnectionPool;
import com.elesson.pioneer.dao.util.DBConnection;
import com.elesson.pioneer.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoImpl implements MovieDao {

    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);

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
    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM movies");
            while(rs.next()) {
                Movie movie = new Movie(rs.getInt("movies.mid"),
                        rs.getString("movies.name"),
                        rs.getString("movies.genre"),
                        rs.getInt("movies.duration"),
                        rs.getInt("movies.year"));
                movies.add(movie);
                logger.info("Movie obtained: " + movie.toString());
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e);
        }
        return movies;
    }

    @Override
    public Movie save(Movie movie) {
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst;
            if(movie.isNew()) {
                String query = "INSERT INTO movies (name, genre, duration, year) VALUES (?, ?, ?, ?)";
                pst = con.prepareInsertStatement(query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear());
                if(pst.executeUpdate()==1) {
                    ResultSet rs = pst.getGeneratedKeys();
                    rs.next();
                    movie.setId(rs.getInt(1));
                    rs.close();
                }
                logger.info("New film created with id={}", movie.getId());
            } else {
                String query = "UPDATE movies SET name=?, genre=?, duration=?, year=? WHERE mid=?";
                pst = con.prepareStatement(query, movie.getName(), movie.getGenre(), movie.getDuration(), movie.getYear(), movie.getId());

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
        int resultRows = 0;
        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            String query = "DELETE FROM movies WHERE mid=?;";
            PreparedStatement pst = con.prepareStatement(query, id);
            resultRows = pst.executeUpdate();
            logger.info("Deleted record id={}", id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
//            throw new DBException("Unable to delete record");
        }
        return resultRows == 1;
    }

    @Override
    public Movie getById(int id) {
        String query = "SELECT * FROM movies WHERE mid=?";
        Movie movie = null;

        try(DBConnection con = ConnectionPool.getPool().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                movie = new Movie(rs.getInt("mid"), rs.getString("name"),
                        rs.getString("genre"), rs.getInt("duration"),
                        rs.getInt("year"));
            }
            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
//            throw new DBException("Unable to obtain record");
        }
        return movie;
    }
}
