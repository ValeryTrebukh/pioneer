package com.elesson.pioneer.service;


import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.User;
import com.elesson.pioneer.service.exception.NotFoundEntityException;

import java.util.List;

/**
 * The interface {@code UserService} contains methods to process requests from controller to DAO layer.
 */
public interface UserService {

    /**
     * Method confirms that object is not null and process it to DAO layer.
     *
     * @param user the {@code User} class object
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void create(User user) throws DuplicateEntityException, DBException;

    /**
     * Method process request to DAO layer and confirms the operation success.
     *
     * @param id the record unique identifier.
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    void delete(int id) throws NotFoundEntityException, DBException;

    /**
     * Method process request to DAO layer and confirms the operation success.
     *
     * @param id the unique identifier
     * @return the instance of {@code User} class
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    User getById(int id) throws NotFoundEntityException, DBException;

    /**
     * Method process request to DAO layer and confirms the operation success.
     *
     * @param email the unique identifier
     * @return the instance of {@code User} class
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    User getByEmail(String email) throws NotFoundEntityException, DBException;

    /**
     * Updates the specified fields of {@code User} object.
     *
     * @param user the {@code User} class object
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void update(User user) throws DuplicateEntityException, DBException;

    /**
     * Returns a list of {@code User} class objects.
     *
     * @return the list of {@code User} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<User> getAll() throws DBException;
}
