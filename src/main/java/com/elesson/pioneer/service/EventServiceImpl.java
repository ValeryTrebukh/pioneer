package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.EventDao;
import com.elesson.pioneer.dao.EventDaoImpl;
import com.elesson.pioneer.model.Event;

import java.time.LocalDate;
import java.util.List;

public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private static EventService service = null;

    private EventServiceImpl() {
        eventDao = EventDaoImpl.getEventDao();
    }

    public static EventService getEventService() {
        if(service ==null) {
            synchronized (EventServiceImpl.class) {
                if(service ==null) {
                    service = new EventServiceImpl();
                }
            }
        }
        return service;
    }

    @Override
    public List<Event> getEvents(LocalDate date) {
        return eventDao.getEvents(date);
    }

    @Override
    public Event getEvent(Integer id) {
        return eventDao.getEvent(id);
    }

    @Override
    public Event delete(int id) {
        return eventDao.delete(id);
    }

    @Override
    public Event save(Event event, Integer mid, Integer sid) {
        return eventDao.save(event, mid, sid);
    }
}
