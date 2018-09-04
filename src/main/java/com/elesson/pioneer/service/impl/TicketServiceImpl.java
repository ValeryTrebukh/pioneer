package com.elesson.pioneer.service.impl;

import com.elesson.pioneer.dao.DaoEntityFactory;
import com.elesson.pioneer.dao.TicketDao;
import com.elesson.pioneer.model.Ticket;
import com.elesson.pioneer.service.TicketService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;

/**
 * Provides implementation of all {@code TicketService} interface methods.
 */
public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;
    private static volatile TicketService service;

    private TicketServiceImpl() {
        ticketDao = DaoEntityFactory.getTicketDao();
    }

    public static TicketService getService() {
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
        return ticketDao.getAllByUserId(id).stream()
                .filter(m -> m.getEvent().getDate().isAfter(LocalDate.now().minusDays(1)))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(List<Ticket> tickets) {
        checkNotFound(tickets, "tickets must not be null");
        ticketDao.save(tickets);
    }
}
