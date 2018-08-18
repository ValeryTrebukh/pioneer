package com.elesson.pioneer.model;

public class Movie extends Entity {
    private String genre;
    private Integer duration;
    private Integer year;

    public Movie() {
    }

    public Movie(Integer id, String name, String genre, Integer duration, Integer year) {
        super(id, name);
        this.genre = genre;
        this.duration = duration;
        this.year = year;
    }

    public Movie(String name, String genre, Integer duration, Integer year) {
        this(null, name, genre, duration, year);
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

}
