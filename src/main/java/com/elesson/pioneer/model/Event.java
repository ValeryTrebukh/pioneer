package com.elesson.pioneer.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The {@code Event} class represents an entity of cinema event.
 * Represents a row in the &quot;users&quot; database table,
 * with each column mapped to a property of this class.
 */
public class Event extends Entity {

    private LocalDate date;
    private Seance seance;
    private Movie movie;

    /**
     * Instantiates a new Event.
     *
     * @param date the date
     */
    public Event(LocalDate date) {
        this(null, date, null, null);
    }

    /**
     * Instantiates a new Event.
     *
     * @param id     the id
     * @param date   the date
     * @param seance the seance
     * @param movie  the movie
     */
    public Event(Integer id, LocalDate date, Seance seance, Movie movie) {
        super(id);
        this.date = date;
        this.seance = seance;
        this.movie = movie;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets seance.
     *
     * @return the seance
     */
    public Seance getSeance() {
        return seance;
    }

    /**
     * Gets movie.
     *
     * @return the movie
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Sets movie.
     *
     * @param movie the movie
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Sets seance.
     *
     * @param seance the seance
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Event event = (Event) o;
        return Objects.equals(date, event.date) &&
                Objects.equals(seance, event.seance) &&
                Objects.equals(movie, event.movie);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, seance, movie);
    }
}
