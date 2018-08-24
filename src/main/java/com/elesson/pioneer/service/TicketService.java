package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Ticket;

import java.util.List;

/**
 * The interface {@code TicketService} contains methods to process requests from controller to DAO layer.
 */
public interface TicketService {

    /**
     * Returns a list of tickets requested for unique event.
     *
     * @return the list of {@code Ticket} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Ticket> getAllTicketsByEventId(Integer id) throws DBException;

    /**
     * Returns a list of tickets requested for unique user.
     *
     * @return the list of {@code Ticket} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Ticket> getAllTicketsByUserId(Integer id) throws DBException;

    /**
     * Method confirms that list is not null or empty and process it to DAO layer.
     *
     * @param tickets the {@code List<Ticket>} class object
     * @return the quantity of stored entries
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void saveAll(List<Ticket> tickets) throws DBException, DuplicateEntityException;
}
