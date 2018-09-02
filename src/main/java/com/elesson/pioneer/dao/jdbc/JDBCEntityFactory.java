package com.elesson.pioneer.dao.jdbc;

import com.elesson.pioneer.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCEntityFactory {

    public static Entity create(Class cl, ResultSet rs) throws SQLException {
        if(cl.equals(User.class)) {
            return new User(rs.getInt("users.uid"),
                    rs.getString("users.name"),
                    rs.getString("users.email"),
                    rs.getString("users.password"),
                    User.Role.valueOf(rs.getString("users.role")));
        } else if (cl.equals(Movie.class)) {
            return new Movie(rs.getInt("movies.mid"),
                    rs.getString("movies.name"),
                    rs.getString("movies.genre"),
                    rs.getInt("movies.duration"),
                    rs.getInt("movies.year"),
                    rs.getBoolean("movies.active"));
        } else if (cl.equals(Event.class)) {
            return new Event(rs.getInt("events.eid"), rs.getDate("events.date").toLocalDate(),
                    (Seance)create(Seance.class, rs), (Movie)create(Movie.class, rs));
        } else if (cl.equals(Seance.class)) {
            return new Seance(rs.getInt("seances.sid"), rs.getTime("seances.time").toLocalTime());
        } else if (cl.equals(Ticket.class)) {
            Ticket t = new Ticket(rs.getInt("tickets.tid"),
                    rs.getInt("tickets.user_id"), rs.getInt("tickets.event_id"),
                    rs.getInt("tickets.row"), rs.getInt("tickets.seat"));
            t.setUser((User) create(User.class, rs));
            t.setEvent((Event) create(Event.class, rs));
            return t;
        } else {
            throw new RuntimeException();
        }
    }
}
