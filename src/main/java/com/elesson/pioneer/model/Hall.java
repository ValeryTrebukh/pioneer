package com.elesson.pioneer.model;


import java.util.List;

public class Hall {

    private Row[] rows;

    public Hall(int maxRows, int maxSeats) {
        this.rows = new Row[maxRows];
        for(int i = 0; i < maxRows; i++) {
            rows[i] = new Row(i+1, maxSeats);
        }
    }

    public Row[] getRows() {
        return rows;
    }

    public void place(List<Ticket> tickets) {
        if(tickets==null) return;
        for (Ticket t : tickets) {
            rows[t.getRow()-1].seats[t.getSeat()-1] = t;
        }
    }

    public class Row {
        private Ticket[] seats;

        Row(int rowNumber, int maxSeats) {
            this.seats = new Ticket[maxSeats];

            for(int i = 0; i < maxSeats; i++) {
                seats[i] = new Ticket(rowNumber, i+1);
            }
        }

        public Ticket[] getSeats() {
            return seats;
        }
    }
}
