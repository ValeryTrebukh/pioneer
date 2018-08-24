package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Ticket;

import java.util.List;

/**
 * The {@code BaseDao} interface provides the methods for interaction with database.
 *
 */
public interface TicketDao {

    /**
     * Retrieves the database for a list of {@code Ticket} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the id of cinema event
     * @return the list of {@code Ticket} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Ticket> getAllByEventId(Integer id) throws DBException;

    /**
     * Retrieves the database for a list of {@code Ticket} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the id of user (owner of the ticket)
     * @return the list of {@code Ticket} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Ticket> getAllByUserId(Integer id) throws DBException;

    /**
     * Inserts new records to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param tickets the {@code Ticket} class objects to be stored in database
     * @return number of inserted rows
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    int saveAll(List<Ticket> tickets) throws DBException, DuplicateEntityException;
}
