package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Movie;

import java.util.List;

public interface MovieDao {

    /**
     * Retrieves the database for a list of {@code Movie} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @return the list of {@code Movie} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Movie> getAll() throws DBException;

    /**
     * Retrieves the database for single object of {@code Movie} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the unique identifier
     * @return the instance of {@code Movie} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    Movie getById(int id) throws DBException;

    /**
     * Inserts new record to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param entity the {@code Movie} class object to be stored in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    Movie save(Movie entity) throws DuplicateEntityException, DBException;

    /**
     * Deletes single record from database.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the record unique identifier.
     * @return true if a single record removed.
     * @throws DBException the general exception to cover all SQL exceptions
     */
    boolean delete(int id) throws DBException;

}
