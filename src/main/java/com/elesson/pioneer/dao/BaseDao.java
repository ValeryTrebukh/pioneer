package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Entity;

import java.time.LocalDate;
import java.util.List;

/**
 * The {@code BaseDao} interface provides the parameterized methods for interaction with database.
 *
 */
public interface BaseDao {

    /**
     * Retrieves the database for a list of {@code Entity} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @return the list of {@code Entity} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> List<T> getAll() throws DBException;

    /**
     * Retrieves the database for single object of {@code Entity} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the unique identifier
     * @return the instance of {@code Entity} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> T getById(int id) throws DBException;

    /**
     * Inserts new record to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param entity the {@code Entity} class object to be stored in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    boolean save(Entity entity) throws DuplicateEntityException, DBException;

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
     * Returns a single object of {@code Entity} class object.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param value the unique identifier
     * @return the instance of {@code Entity} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> T getByValue(String value) throws DBException;

    /**
     * Retrieves the database for single object of {@code Entity} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param date the identifier
     * @return the instance of {@code Entity} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> List<T> getAllByDate(LocalDate date) throws DBException;

    /**
     * Retrieves the database for a list of {@code Entity} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @return the list of {@code Entity} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> List<T> getActive() throws DBException;
}
