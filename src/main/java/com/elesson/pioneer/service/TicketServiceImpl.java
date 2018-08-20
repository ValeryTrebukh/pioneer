package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.TicketDao;
import com.elesson.pioneer.dao.TicketDaoImpl;
import com.elesson.pioneer.model.Ticket;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;
    private static TicketService service = null;

    private TicketServiceImpl() {
        ticketDao = TicketDaoImpl.getTicketDao();
    }

    public static TicketService getTicketService() {
        if(service ==null) {
            synchronized (TicketServiceImpl.class) {
                if(service ==null) {
                    service = new TicketServiceImpl();
                }
            }
        }
        return service;
    }

    @Override
    public List<Ticket> getAllTicketsByEventId(Integer id) {
        return ticketDao.getAllTicketsByEventId(id);
    }

    @Override
    public List<Ticket> getAllTicketsByUserId(Integer id) {
        return ticketDao.getAllTicketsByUserId(id);
    }

    @Override
    public int save(List<Ticket> tickets) {
        return ticketDao.save(tickets);
    }
}
