package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Event;
import com.elesson.pioneer.service.exception.NotFoundEntityException;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface {@code EventService} contains methods to process requests from controller to DAO layer.
 */
public interface EventService {

    /**
     * Returns a list of {@code Event} class objects.
     *
     * @param date the date for which Events requested
     * @return the list of {@code Event} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Event> getEvents(LocalDate date) throws DBException;

    /**
     * Method process request to DAO layer and confirms the operation success.
     *
     * @param id the unique identifier
     * @return the instance of {@code Event} class
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    Event getEvent(Integer id) throws NotFoundEntityException, DBException;

    /**
     * Method process request to DAO layer and confirms the operation success.
     *
     * @param id the record unique identifier.
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    void delete(int id) throws NotFoundEntityException, DBException;

    /**
     * Method confirms that object is not null and process it to DAO layer.
     *
     * @param event the {@code Event} class object to be saved
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void save(Event event) throws DuplicateEntityException, DBException;
}
