package com.elesson.pioneer.service;

import com.elesson.pioneer.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    List<Event> getEvents(LocalDate date);

    Event getEvent(Integer id);

    boolean delete(int id);

    Event save(Event event);
}
