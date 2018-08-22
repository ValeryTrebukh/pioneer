package com.elesson.pioneer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie extends Entity {
    private String name;
    private String genre;
    private Integer duration;
    private Integer year;
    private Boolean active;

    public Movie() {
    }

    public Movie(Integer id) {
        super(id);
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

    public Movie(ResultSet rs) throws SQLException {
        this(rs.getInt("m.mid"),
                rs.getString("m.name"),
                rs.getString("m.genre"),
                rs.getInt("m.duration"),
                rs.getInt("m.year"),
                rs.getBoolean("active"));
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
