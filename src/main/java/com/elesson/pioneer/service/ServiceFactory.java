package com.elesson.pioneer.service;

import com.elesson.pioneer.service.impl.EventServiceImpl;
import com.elesson.pioneer.service.impl.MovieServiceImpl;
import com.elesson.pioneer.service.impl.TicketServiceImpl;
import com.elesson.pioneer.service.impl.UserServiceImpl;

public class ServiceFactory {

    public static UserService getUserService() {
        return UserServiceImpl.getService();
    }

    public static MovieService getMovieService() {
        return MovieServiceImpl.getService();
    }

    public static EventService getEventService() {
        return EventServiceImpl.getService();
    }

    public static TicketService getTicketService() {
        return TicketServiceImpl.getService();
    }
}
