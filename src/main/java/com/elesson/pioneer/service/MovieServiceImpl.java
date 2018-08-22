package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.BaseDao;
import com.elesson.pioneer.dao.DaoFactory;
import com.elesson.pioneer.model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private BaseDao movieDao;
    private static MovieService service = null;

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

    @Override
    public boolean create(Movie movie) {
        return movieDao.save(movie);
    }

    @Override
    public boolean delete(int id) {
        return movieDao.delete(id);
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
    public List<Movie> getAllMovies() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getActiveMovies() {
        return movieDao.getActive();
    }
}
