package com.elesson.pioneer.dao;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Entity;

import java.util.List;

/**
 * The {@code JDBCDao} interface provides methods to perform CRUD operations with database
 * using jdbc driver.
 *
 */
public interface Dao {

    /**
     * Deletes single record from database.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param query the SQL query string.
     * @param id the record unique identifier.
     * @return true if a single record removed.
     * @throws DBException the general exception to cover all SQL exceptions
     */
    boolean delete(String query, int id) throws DBException;

    /**
     * Retrieves the database for single object of {@code Entity} class.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param cl the class of object to be retrieved from database
     * @param query the SQL query string.
     * @param values the parameters for query.
     * @return the instance of {@code Entity} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> T get(Class cl, String query, Object... values) throws DBException;

    /**
     * Retrieves the database for the list of objects.
     * Suppress all SQL exceptions and throws own general one.
     *
     * @param cl the class of objects to be retrieved from database
     * @param query the SQL query string.
     * @param values the parameters for query.
     * @return the instance of {@code Entity} class
     * @throws DBException the general exception to cover all SQL exceptions
     */
    <T extends Entity> List<T> getAll(Class cl, String query, Object... values) throws DBException;

    /**
     * Inserts new record to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param query the SQL query string.
     * @param values the parameters for query.
     * @param entity the {@code T} class object to be stored in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    <T extends Entity> T save(T entity, String query, Object... values) throws DBException, DuplicateEntityException;

    /**
     * Inserts set of new records to database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param query the SQL query string.
     * @param values the parameters for query.
     * @return number of inserted records
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    <T extends Entity> int save(String query, Object[][] values) throws DBException, DuplicateEntityException;

    /**
     * Updates a record in database.
     * Suppress all SQL exceptions and throws own ones.
     *
     * @param query the SQL query string.
     * @param values the parameters for query.
     * @param entity the {@code T} class object to be updated in database
     * @return true if operation successful
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException              in all other cases.
     */
    <T extends Entity> T update(T entity, String query, Object... values) throws DBException;
}
