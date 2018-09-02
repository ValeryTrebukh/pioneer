package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.DBHelper;
import com.elesson.pioneer.model.Movie;
import org.junit.Before;
import org.junit.Test;

import static com.elesson.pioneer.dao.TestData.*;
import static org.junit.Assert.*;


public class MovieDaoImplTest {

    private MovieDao movieDao = MovieDaoImpl.getMovieDao();

    @Before
    public void setUp() throws Exception {
        DBHelper.initDB();
    }

    @Test
    public void getAll() {
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_2, MOVIE_3, MOVIE_4, MOVIE_5}, movieDao.getAll().toArray());
    }

    @Test
    public void save() {
        movieDao.save(MOVIE_6);
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_2, MOVIE_3, MOVIE_4, MOVIE_5, MOVIE_6}, movieDao.getAll().toArray());
    }

    @Test
    public void update() {
        movieDao.save(MOVIE_7);
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_7, MOVIE_3, MOVIE_4, MOVIE_5}, movieDao.getAll().toArray());
    }

    @Test
    public void delete() {
        movieDao.delete(2);
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_3, MOVIE_4, MOVIE_5}, movieDao.getAll().toArray());
    }

    @Test
    public void getById() {
        assertEquals(MOVIE_1, movieDao.getById(1));
        assertEquals(MOVIE_4, movieDao.getById(4));
    }

    @Test
    public void getNotFound() throws Exception {
        assertNull(movieDao.getById(11));
    }
}