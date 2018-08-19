package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Ticket;

import java.util.List;

public interface TicketDao {

    List<Ticket> getAllTicketsByEventId(Integer id);

    List<Ticket> getAllTicketsByUserId(Integer id);

    Ticket delete(Integer id);

    Ticket save(Ticket ticket);
}
