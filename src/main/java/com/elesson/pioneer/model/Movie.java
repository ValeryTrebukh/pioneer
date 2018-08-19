package com.elesson.pioneer.model;

public class Movie extends Entity {
    private String name;
    private String genre;
    private Integer duration;
    private Integer year;
    private Boolean active;

    public Movie() {
    }

    public Movie(Integer id, String name, String genre, Integer duration, Integer year, Boolean active) {
        super(id);
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.year = year;
        this.active = active;
    }

    public Movie(String name, String genre, Integer duration, Integer year, Boolean active) {
        this(null, name, genre, duration, year, active);
    }

    public String getName() {
        return name;
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

    public Boolean isActive() {
        return active;
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
