package com.elesson.pioneer.service;

import com.elesson.pioneer.dao.BaseDao;
import com.elesson.pioneer.dao.DaoFactory;
import com.elesson.pioneer.model.Event;

import java.time.LocalDate;
import java.util.List;

public class EventServiceImpl implements EventService {

    private BaseDao eventDao;
    private static EventService service = null;

    private EventServiceImpl() {
        eventDao = DaoFactory.getDao(DaoFactory.DaoType.EVENT);
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
        return eventDao.getAllByDate(date);
    }

    @Override
    public Event getEvent(Integer id) {
        return eventDao.getById(id);
    }

    @Override
    public boolean delete(int id) {
        return eventDao.delete(id);
    }

    @Override
    public boolean save(Event event) {
        return eventDao.save(event);
    }
}
