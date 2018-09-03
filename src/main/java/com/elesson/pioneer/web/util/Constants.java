package com.elesson.pioneer.web.util;

public interface Constants {

    // JSP pages relative Paths

    String LOGIN_JSP = "jsp/login.jsp";
    String REGISTRATION_JSP = "jsp/registration.jsp";
    String EVENT_FORM_JSP = "jsp/eventForm.jsp";
    String EVENT_VIEW_JSP = "jsp/eventView.jsp";
    String MOVIE_FORM_JSP = "jsp/movieForm.jsp";
    String MOVIES_JSP = "jsp/movies.jsp";
    String MY_TICKETS_JSP = "jsp/myTickets.jsp";
    String SCHEDULE_JSP = "jsp/schedule.jsp";
    String USER_FORM_JSP = "jsp/userForm.jsp";
    String USERS_JSP = "jsp/users.jsp";


    // Servlets URLs

    String EVENT = "event";
    String MOVIES = "movies";
    String SCHEDULE = "schedule";
    String USERS = "users";


    // Request parameter/attribute names

    String A_ACTION = "action";
    String A_EVENT = "event";
    String A_EVENTS = "events";
    String A_MOVIE = "movie";
    String A_MOVIES = "movies";
    String A_USER = "user";
    String A_USERS = "users";
    String A_TICKETS = "tickets";
    String A_DATE = "date";
    String A_LANG = "language";

    String A_AUTH_USER = "authUser";
    String A_AUTH_USER_EMAIL = "authUserEmail";
    String A_AUTH_USER_PASS = "authUserPass";

    String A_PAGE = "page";
    String A_PAGE_SIZE = "pageSize";
    String A_PAGES_COUNT = "pagesCount";

    String A_NEXT_WEEK = "nextWeek";

    String A_DUPLICATE = "duplicate";
    String A_ERR = "error";

    String A_EID = "eid";
    String A_MID = "mid";
    String A_SID = "sid";
    String A_TID = "tid";
    String A_UID = "userid";

    String A_NAME = "name";
    String A_GENRE = "genre";
    String A_DURATION = "duration";
    String A_YEAR = "year";
    String A_STATUS = "status";

    String A_REG_NAME = "regName";
    String A_REG_EMAIL = "regEmail";
    String A_REG_PASS = "regPass";
    String A_REG_PASS2 = "confPass";
    String A_ROLE= "role";

    String A_HALL = "hall";
    String A_HALL_ROWS = "hallRows";
    String A_HALL_SEATS = "hallSeats";


    // Actions used in switch-case

    String CREATE = "create";
    String EDIT = "edit";
    String DELETE = "delete";
    String VIEW = "view";
}
