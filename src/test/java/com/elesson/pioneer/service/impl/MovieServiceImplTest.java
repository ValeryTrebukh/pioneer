package com.elesson.pioneer.service.impl;

import com.elesson.pioneer.dao.MovieDao;
import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.service.MovieService;
import com.elesson.pioneer.service.ServiceFactory;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Arrays;

import static com.elesson.pioneer.dao.TestData.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MovieServiceImplTest {

    @Mock
    private MovieDao dao;

    @InjectMocks
    private MovieService service = ServiceFactory.getMovieService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        when(dao.save(MOVIE_1)).thenReturn(MOVIE_1);
        service.create(MOVIE_1);
    }

    @Test(expected = NotFoundEntityException.class)
    public void createNull() {
        service.create(null);
    }
    @Test
    public void delete() {
        when(dao.delete(1)).thenReturn(true);
        service.delete(1);
    }

    @Test(expected = NotFoundEntityException.class)
    public void deleteNotFound() {
        when(dao.delete(1)).thenReturn(true);
        service.delete(2);
    }

    @Test
    public void get() {
        when(dao.getById(1)).thenReturn(MOVIE_1);
        service.get(1);
    }

    @Test(expected = NotFoundEntityException.class)
    public void getNotFound() {
        when(dao.getById(1)).thenReturn(null);
        service.get(1);
    }

    @Test
    public void update() {
        when(dao.save(MOVIE_1)).thenReturn(MOVIE_1);
        service.update(MOVIE_1);
    }

    @Test
    public void getAllMovies() {
        when(dao.getAll()).thenAnswer(i -> new ArrayList<>(Arrays.asList(MOVIE_1, MOVIE_2)));
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_2}, service.getAllMovies().toArray());
    }

    @Test
    public void getActiveMovies() {
        when(dao.getAll()).thenAnswer(i -> new ArrayList<>(Arrays.asList(MOVIE_1, MOVIE_2, INACTIVE)));
        assertArrayEquals(new Movie[]{MOVIE_1, MOVIE_2}, service.getActiveMovies().toArray());
    }
}