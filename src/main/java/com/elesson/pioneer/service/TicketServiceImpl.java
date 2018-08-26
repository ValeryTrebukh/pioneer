package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.TicketDao;
import com.elesson.pioneer.dao.TicketDaoImpl;
import com.elesson.pioneer.model.Ticket;

import java.util.List;

import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;

/**
 * Provides implementation of all {@code TicketService} interface methods.
 */
public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;
    private static volatile TicketService service;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTicketsByEventId(Integer id) {
        return ticketDao.getAllByEventId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTicketsByUserId(Integer id) {
        //TODO date after today
        return ticketDao.getAllByUserId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(List<Ticket> tickets) {
        checkNotFound(tickets, "tickets must not be null");
        ticketDao.saveAll(tickets);
    }
}
