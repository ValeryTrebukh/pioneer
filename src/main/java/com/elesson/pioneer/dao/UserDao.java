package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Entity;
import com.elesson.pioneer.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Retrieves the database for a list of {@code User} class objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @return the list of {@code User} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<User> getAll() throws DBException;

    /**
     * Retrieves the database for single object of {@code User} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param id the unique identifier
     * @return the instance of {@code User} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    User getById(int id) throws DBException;

    /**
     * Inserts new record to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param user the {@code User} class object to be stored in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    User save(User user) throws DuplicateEntityException, DBException;

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
     * Returns a single object of {@code User} class object.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param email the unique identifier
     * @return the instance of {@code User} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    User getByEmail(String email) throws DBException;

}
