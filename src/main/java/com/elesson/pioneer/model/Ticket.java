package com.elesson.pioneer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a row in the &quot;tickets&quot; database table,
 * with each column mapped to a property of this class.
 */
public class Ticket extends Entity {

    private Integer userId;
    private Integer eventId;
    private Integer row;
    private Integer seat;
    private User user;
    private Event event;

    /**
     * Instantiates a new Ticket.
     *
     * @param id      the id
     * @param userId  the user id
     * @param eventId the event id
     * @param row     the row
     * @param seat    the seat
     */
    public Ticket(Integer id, Integer userId, Integer eventId, Integer row, Integer seat) {
        super(id);
        this.userId = userId;
        this.eventId = eventId;
        this.row = row;
        this.seat = seat;
    }

    /**
     * Instantiates a new Ticket.
     *
     * @param userId  the user id
     * @param eventId the event id
     * @param row     the row
     * @param seat    the seat
     */
    public Ticket(Integer userId, Integer eventId, Integer row, Integer seat) {
        this(null, userId, eventId, row, seat);
    }

    /**
     * Instantiates a new Ticket.
     *
     * @param eventId the event id
     * @param row     the row
     * @param seat    the seat
     */
    public Ticket(Integer eventId, Integer row, Integer seat) {
        this(null, eventId, row, seat);
    }

    /**
     * Instantiates a new Ticket.
     *
     * @param row  the row
     * @param seat the seat
     */
    public Ticket(Integer row, Integer seat) {
        this(null, row, seat);
    }

    /**
     * Instantiates a new Ticket.
     *
     * @param rs the ResultSet object
     * @throws SQLException the sql exception
     */
    public Ticket(ResultSet rs) throws SQLException {
        this(rs.getInt("tickets.tid"),
                rs.getInt("tickets.user_id"), rs.getInt("tickets.event_id"),
                rs.getInt("tickets.row"), rs.getInt("tickets.seat"));
        this.user = new User(rs);
        this.event = new Event(rs);
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Gets event id.
     *
     * @return the event id
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public Integer getRow() {
        return row;
    }

    /**
     * Gets seat.
     *
     * @return the seat
     */
    public Integer getSeat() {
        return seat;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userId=" + userId +
                ", eventId=" + eventId +
                ", row=" + row +
                ", seat=" + seat +
                '}';
    }
}
