package com.elesson.pioneer.model;


import java.util.List;

/**
 * Represents a cinema hall
 */
public class Hall {

    private Row[] rows;

    /**
     * Instantiates a new Hall.
     *
     * @param maxRows  the max rows
     * @param maxSeats the max seats
     */
    public Hall(int maxRows, int maxSeats) {
        this.rows = new Row[maxRows];
        for(int i = 0; i < maxRows; i++) {
            rows[i] = new Row(i+1, maxSeats);
        }
    }

    /**
     * Get rows row [ ].
     *
     * @return the row [ ]
     */
    public Row[] getRows() {
        return rows;
    }

    /**
     * Place.
     *
     * @param tickets the tickets
     */
    public void place(List<Ticket> tickets) {
        if(tickets==null) return;
        for (Ticket t : tickets) {
            rows[t.getRow()-1].seats[t.getSeat()-1] = t;
        }
    }

    /**
     * Represents a row of seats in the cinema hall.
     */
    public class Row {
        private Ticket[] seats;

        /**
         * Instantiates a new Row.
         *
         * @param rowNumber the row number
         * @param maxSeats  the max seats
         */
        Row(int rowNumber, int maxSeats) {
            this.seats = new Ticket[maxSeats];

            for(int i = 0; i < maxSeats; i++) {
                seats[i] = new Ticket(rowNumber, i+1);
            }
        }

        /**
         * Get seats ticket [ ].
         *
         * @return the ticket [ ]
         */
        public Ticket[] getSeats() {
            return seats;
        }
    }
}
