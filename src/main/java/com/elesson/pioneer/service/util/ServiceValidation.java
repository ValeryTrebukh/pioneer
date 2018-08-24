package com.elesson.pioneer.service.util;


import com.elesson.pioneer.service.exception.NotFoundEntityException;

/**
 * This class serves to validate the data coming from controller or DAO layer.
 */
public class ServiceValidation {

    /**
     * Throws custom exception if first param is false.
     */
    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    /**
     * Throws custom exception if first param is false.
     */
    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundEntityException("Not found entity with " + msg);
        }
    }

    /**
     * Throws custom exception if first param is null.
     */
    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    /**
     * Throws custom exception if first param is null.
     */
    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }
}
