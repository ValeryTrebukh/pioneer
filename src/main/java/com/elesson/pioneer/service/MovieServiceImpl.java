package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.MovieDao;
import com.elesson.pioneer.dao.MovieDaoImpl;
import com.elesson.pioneer.model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private MovieDao movieDao;
    private static MovieService service = null;

    private MovieServiceImpl() {
        movieDao = MovieDaoImpl.getMovieDao();
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

    @Override
    public Movie create(Movie movie) {
        return movieDao.save(movie);
    }

    @Override
    public void delete(int id) {
        movieDao.delete(id);
    }

    @Override
    public Movie get(int id) {
        return movieDao.getById(id);
    }

    @Override
    public void update(Movie movie) {
        movieDao.save(movie);
    }

    @Override
    public List<Movie> getMovies() {
        return movieDao.getMovies();
    }
}
