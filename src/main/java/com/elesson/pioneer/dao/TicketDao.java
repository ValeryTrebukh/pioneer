package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Ticket;

import java.util.List;

public interface TicketDao {

    List<Ticket> getAllByEventId(Integer id);

    List<Ticket> getAllByUserId(Integer id);

    int saveAll(List<Ticket> tickets);
}
