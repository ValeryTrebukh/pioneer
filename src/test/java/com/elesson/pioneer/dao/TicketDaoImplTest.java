package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.util.DBHelper;
import com.elesson.pioneer.model.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.elesson.pioneer.dao.TestData.*;
import static org.junit.Assert.*;

public class TicketDaoImplTest {


    private TicketDao ticketDao = DaoEntityFactory.getTicketDao();

    @Before
    public void setUp() throws Exception {
        DBHelper.initDB();
    }

    @Test
    public void getAllByEventId() {
        assertArrayEquals(new Ticket[]{TICKET_7, TICKET_8}, ticketDao.getAllByEventId(1).toArray());
    }

    @Test
    public void getAllByUserId() {
        assertArrayEquals(new Ticket[]{TICKET_7, TICKET_8}, ticketDao.getAllByUserId(5).toArray());
    }

    @Test
    public void save() {
        List<Ticket> l = new ArrayList<>();
        l.add(TICKET_9);
        l.add(TICKET_10);
        ticketDao.save(l);
        assertArrayEquals(new Ticket[]{TICKET_7, TICKET_8, TICKET_9, TICKET_10}, ticketDao.getAllByUserId(5).toArray());
    }
}