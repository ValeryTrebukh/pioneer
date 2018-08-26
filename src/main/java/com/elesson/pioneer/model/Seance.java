package com.elesson.pioneer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * Instantiates a new Seance.
     *
     * @param rs the ResultSet object
     * @throws SQLException the sql exception
     */
    public Seance(ResultSet rs) throws SQLException {
        this(rs.getInt("s.sid"), rs.getTime("s.time").toLocalTime());
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
