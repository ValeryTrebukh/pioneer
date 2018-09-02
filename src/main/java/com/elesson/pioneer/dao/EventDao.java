package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Event;

import java.time.LocalDate;
import java.util.List;


/**
 * The {@code EventDao} interface provides the methods for interaction with database.
 *
 */
public interface EventDao {

    /**
     * Retrieves the database for single object of {@code Event} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the unique identifier
     * @return the instance of {@code Event} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    Event getById(int id) throws DBException;

    /**
     * Inserts new record to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param entity the {@code Event} class object to be stored in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    Event save(Event entity) throws DuplicateEntityException, DBException;

    /**
     * Deletes single record from database.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the record unique identifier.
     * @return true if a single record removed.
     * @throws DBException the general exception to cover all SQL exceptions
     */
    boolean delete(int id) throws DBException;

    /**
     * Retrieves the database for single object of {@code Event} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param date the identifier
     * @return the instance of {@code Event} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Event> getByDate(LocalDate date) throws DBException;


}
