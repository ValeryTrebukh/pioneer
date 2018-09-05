package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.exception.DBException;
import com.elesson.pioneer.dao.exception.DuplicateEntityException;
import com.elesson.pioneer.model.Movie;
import com.elesson.pioneer.service.exception.NotFoundEntityException;

import java.util.List;

/**
 * The interface {@code MovieService} contains methods to process requests from controller to DAO layer.
 */
public interface MovieService {

    /**
     * Method confirms that object is not null and process it to DAO layer.
     *
     * @param movie the {@code Movie} class object
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void create(Movie movie) throws DBException;

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
     * @return the instance of {@code Movie} class
     * @throws DBException the general exception to cover all SQL exceptions
     * @throws NotFoundEntityException if no record found
     */
    Movie get(int id) throws NotFoundEntityException, DBException;

    /**
     * Updates the specified fields of {@code Movie} object.
     *
     * @param movie the {@code Movie} class object
     * @throws DuplicateEntityException in case of non-unique values (database restrictions).
     * @throws DBException in all other cases.
     */
    void update(Movie movie) throws DBException;

    /**
     * Returns a list of movies.
     *
     * @return the list of {@code Movie} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Movie> getAllMovies() throws DBException;

    /**
     * Returns a list of movies which have parameter 'active' set to true.
     *
     * @return the list of {@code Movie} class objects
     * @throws DBException the general exception to cover all SQL exceptions
     */
    List<Movie> getActiveMovies() throws DBException;

}
