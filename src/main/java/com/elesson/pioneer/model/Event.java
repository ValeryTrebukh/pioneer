package com.elesson.pioneer.model;

import java.time.LocalDate;

public class Event extends Entity {

    private LocalDate date;
    private Seance seance;
    private Movie movie;

    public Event(LocalDate date) {
        this(null, date, null, null);
    }

    public Event(Integer id, LocalDate date, Seance seance, Movie movie) {
        super(id);
        this.date = date;
        this.seance = seance;
        this.movie = movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public Seance getSeance() {
        return seance;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", seance=" + seance +
                '}';
    }
}
