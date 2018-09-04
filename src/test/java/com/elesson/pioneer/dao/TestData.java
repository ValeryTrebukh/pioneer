package com.elesson.pioneer.dao;

import com.elesson.pioneer.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestData {

    public static final User USER_1 = new User(1, "Ромашова Ольга", "ro@gmail.com", "1111", User.Role.ADMIN);
    public static final User USER_2 = new User(2, "Тищенко Екатерина", "te@gmail.com", null, User.Role.CLIENT);
    public static final User USER_3 = new User(3, "Потапова Алеся", "pa@gmail.com", null, User.Role.CLIENT);
    public static final User USER_4 = new User(4, "Смирнова Ольга", "so@gmail.com", null, User.Role.CLIENT);
    public static final User USER_5 = new User(5, "Буланов Тимур", "bt@gmail.com", null, User.Role.CLIENT);
    static final User USER_6 = new User("Ромашова Ирина", "ri@gmail.com", "test", User.Role.CLIENT);
    static final User USER_6_2 = new User("Ромашова Ирина", "ri@gmail.com", "test", User.Role.CLIENT);
    static final User USER_7 = new User(2, "Смирнова Екатерина", "te@gmail.com", "test", User.Role.CLIENT);

    static final Movie MOVIE_1 = new Movie(1, "Terminator", "science-fiction action", 107, 1984, true);
    static final Movie MOVIE_2 = new Movie(2, "Titanic", "epic romance", 195, 1997, true);
    static final Movie MOVIE_3 = new Movie(3, "Fifty Shades of Grey", "erotic romantic drama", 125, 2015, true);
    static final Movie MOVIE_4 = new Movie(4, "Monsters, Inc.", "computer-animated comedy", 92, 2001, true);
    static final Movie MOVIE_5 = new Movie(5, "Левиафан", "драма", 142, 2014, true);
    static final Movie MOVIE_6 = new Movie("Terminator: Judgement Day", "science-fiction action", 137, 1991, true);
    static final Movie MOVIE_7 = new Movie(2, "Титаник", "фильм-катастрофа", 195, 1997, true);

    static final Seance SEANCE_1 = new Seance(1, LocalTime.of(9, 0));
    static final Seance SEANCE_2 = new Seance(2, LocalTime.of(13, 0));
    static final Seance SEANCE_3 = new Seance(3, LocalTime.of(18, 0));
    static final Seance SEANCE_4 = new Seance(4, LocalTime.of(22, 0));

    static final Event EVENT_1_13 = new Event(1, LocalDate.parse("2018-09-13"), SEANCE_2, MOVIE_1);
    static final Event EVENT_2_13 = new Event(2, LocalDate.parse("2018-09-13"), SEANCE_4, MOVIE_3);
    static final Event EVENT_7_13 = new Event(7, LocalDate.parse("2018-09-13"), SEANCE_1, MOVIE_4);

    static final Ticket TICKET_1 = new Ticket(1, 2, 2, 4 ,4);
    static final Ticket TICKET_2 = new Ticket(2, 2, 2, 4 ,5);
    static final Ticket TICKET_3 = new Ticket(3, 2, 2, 4 ,6);
    static final Ticket TICKET_4 = new Ticket(4, 3, 2, 3 ,4);
    static final Ticket TICKET_5 = new Ticket(5, 3, 2, 3 ,5);
    static final Ticket TICKET_6 = new Ticket(6, 3, 2, 3 ,6);
    static final Ticket TICKET_7 = new Ticket(7, 5, 1, 4 ,5);
    static final Ticket TICKET_8 = new Ticket(8, 5, 1, 4 ,6);
    static final Ticket TICKET_9 = new Ticket(9, 5, 1, 4 ,7);
}
