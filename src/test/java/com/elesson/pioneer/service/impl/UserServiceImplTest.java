package com.elesson.pioneer.service.impl;

import com.elesson.pioneer.dao.UserDao;
import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.ServiceFactory;
import com.elesson.pioneer.service.UserService;
import com.elesson.pioneer.service.exception.NotFoundEntityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static com.elesson.pioneer.dao.TestData.*;
import static com.elesson.pioneer.dao.TestData.USER_5;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserDao dao;

    @InjectMocks
    private UserService service = ServiceFactory.getUserService();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        when(dao.save(USER_1)).thenReturn(USER_1);
        service.create(USER_1);
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
    public void getById() {
        when(dao.getById(1)).thenReturn(USER_1);
        assertEquals(USER_1, service.getById(1));
    }

    @Test(expected = NotFoundEntityException.class)
    public void getNotFound() {
        when(dao.getById(1)).thenReturn(USER_1);
        service.getById(2);
    }

    @Test(expected = DBException.class)
    public void getThrowsDBException() {
        when(dao.getById(11)).thenThrow(DBException.class);
        service.getById(11);
    }

    @Test
    public void getByEmail() {
        when(dao.getByEmail("ro@gmail.com")).thenReturn(USER_1);
        assertEquals(USER_1, service.getByEmail("ro@gmail.com"));
    }

    @Test(expected = NotFoundEntityException.class)
    public void getByEmailNotFound() {
        service.getByEmail(null);
    }

    @Test (expected = NotFoundEntityException.class)
    public void getByEmailNotFound2() {
        when(dao.getByEmail("ro@gmail.com")).thenReturn(USER_1);
        assertEquals(USER_1, service.getByEmail("ra@gmail.com"));
    }

    @Test
    public void update() {
        when(dao.save(USER_1)).thenReturn(USER_1);
        service.update(USER_1);
    }

    @Test
    public void getAll() {
        when(dao.getAll()).thenAnswer(i -> Arrays.asList(USER_1, USER_2, USER_3, USER_4, USER_5));
        assertArrayEquals(new User[]{USER_1, USER_2, USER_3, USER_4, USER_5}, service.getAll().toArray());
    }
}