package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.DBHelper;
import com.elesson.pioneer.model.Event;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.elesson.pioneer.dao.DaoFactory.getDao;
import static com.elesson.pioneer.dao.TestData.*;
import static org.junit.Assert.*;

public class EventDaoImplTest {


    private BaseDao eventDao = getDao(DaoFactory.DaoType.EVENT);

    @Before
    public void setUp() throws Exception {
        DBHelper.initDB();
    }

    @Test
    public void getById() {
        assertEquals(EVENT_1_13, eventDao.getById(1));
    }

    @Test
    public void getAllByDate() {
        assertArrayEquals(new Event[]{EVENT_1_13, EVENT_2_13}, eventDao.getAllByDate(LocalDate.parse("2018-09-13")).toArray());
    }

    @Test
    public void save() {
        eventDao.save(EVENT_7_13);
        assertArrayEquals(new Event[]{EVENT_7_13, EVENT_1_13, EVENT_2_13}, eventDao.getAllByDate(LocalDate.parse("2018-09-13")).toArray());
    }

    @Test
    public void delete() {
        eventDao.delete(2);
        assertArrayEquals(new Event[]{EVENT_1_13}, eventDao.getAllByDate(LocalDate.parse("2018-09-13")).toArray());
    }
}