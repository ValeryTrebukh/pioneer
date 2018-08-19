package com.elesson.pioneer.model;

public class Ticket extends Entity {

    private Integer userId;
    private Integer eventId;
    private Integer row;
    private Integer seat;

    public Ticket(Integer id, Integer userId, Integer eventId, Integer row, Integer seat) {
        super(id);
        this.userId = userId;
        this.eventId = eventId;
        this.row = row;
        this.seat = seat;
    }

    public Ticket(Integer userId, Integer eventId, Integer row, Integer seat) {
        this(null, userId, eventId, row, seat);
    }

    public Ticket(Integer eventId, Integer row, Integer seat) {
        this(null, eventId, row, seat);
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getSeat() {
        return seat;
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
