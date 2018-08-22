package com.elesson.pioneer.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class Seance extends Entity {

    private LocalTime start;

    public Seance(Integer id, LocalTime start) {
        super(id);
        this.start = start;
    }

    public Seance(Integer id) {
        super(id);
    }

    public Seance(ResultSet rs) throws SQLException {
        this(rs.getInt("s.sid"), rs.getTime("s.time").toLocalTime());
    }

    public LocalTime getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start + "";
    }

}
