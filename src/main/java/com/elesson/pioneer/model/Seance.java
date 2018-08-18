package com.elesson.pioneer.model;

import java.time.LocalTime;

public class Seance extends Entity {

    private LocalTime start;

    public Seance(Integer id, LocalTime start) {
        super(id, null);
        this.start = start;
    }

    public LocalTime getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start + "";
    }

}
