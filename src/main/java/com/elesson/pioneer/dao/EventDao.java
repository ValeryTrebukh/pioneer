package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventDao {
    List<Event> getEvents(LocalDate date);

    Event getEvent(Integer id);

    Event save(Event movie);

    Event delete(int id);
}
