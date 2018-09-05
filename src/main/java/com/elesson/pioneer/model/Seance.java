package com.elesson.pioneer.model;

import java.time.LocalTime;

/**
 * Represents a row in the &quot;seances&quot; database table,
 * with each column mapped to a property of this class.
 */
public class Seance extends Entity {

    private LocalTime start;

    /**
     * Instantiates a new Seance.
     *
     * @param id    the id
     */
    public Seance(Integer id) {
        super(id);
    }

    /**
     * Instantiates a new Seance.
     *
     * @param id    the id
     * @param start the start
     */
    public Seance(Integer id, LocalTime start) {
        super(id);
        this.start = start;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalTime getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start + "";
    }

}
