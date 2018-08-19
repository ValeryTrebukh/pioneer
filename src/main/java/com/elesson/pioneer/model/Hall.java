package com.elesson.pioneer.model;


import java.util.List;

public class Hall {

    private Row[] rows;

    public Hall(int maxRows, int maxSeats) {
        this.rows = new Row[maxRows];
        for(int i = 0; i < maxRows; i++) {
            rows[i] = new Row(maxSeats);
        }
    }

    public Row[] getRows() {
        return rows;
    }

    public void place(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            rows[t.getRow()-1].seats[t.getSeat()-1] = t.getUserId();
        }
    }

    public class Row {
        private int[] seats;

        Row(int maxSeats) {
            this.seats = new int[maxSeats];
        }

        public int[] getSeats() {
            return seats;
        }
    }
}
