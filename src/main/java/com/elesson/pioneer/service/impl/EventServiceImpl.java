package com.elesson.pioneer.service.impl;

import com.elesson.pioneer.dao.DaoEntityFactory;
import com.elesson.pioneer.dao.EventDao;
import com.elesson.pioneer.model.Event;
import com.elesson.pioneer.service.EventService;

import java.time.LocalDate;
import java.util.List;

import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFound;
import static com.elesson.pioneer.service.util.ServiceValidation.checkNotFoundWithId;

public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private static volatile EventService service;

    private EventServiceImpl() {
        eventDao = DaoEntityFactory.getEventDao();
    }

    public static EventService getService() {
        if(service ==null) {
            synchronized (EventServiceImpl.class) {
                if(service ==null) {
                    service = new EventServiceImpl();
                }
            }
        }
        return service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Event> getEvents(LocalDate date) {
        return eventDao.getByDate(date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEvent(Integer id) {
        return checkNotFoundWithId(eventDao.getById(id), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        checkNotFoundWithId(eventDao.delete(id), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Event event) {
        checkNotFound(event, "event must not be null");
        checkNotFound(eventDao.save(event), "user must not be null");
    }
}
