DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS seances;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
  uid         INTEGER AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR (255) NOT NULL,
  email       VARCHAR (255) UNIQUE  NOT NULL ,
  password    VARCHAR (255) NOT NULL,
  role        VARCHAR(6) NOT NULL,
  check(role in ('CLIENT', 'ADMIN'))
);

CREATE TABLE movies
(
  mid         INTEGER AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR (255) NOT NULL,
  genre       VARCHAR (255) NOT NULL,
  duration    INTEGER NOT NULL,
  year        INTEGER NOT NULL,
  active      BOOLEAN NOT NULL
);

CREATE TABLE seances
(
  sid         INTEGER AUTO_INCREMENT PRIMARY KEY,
  time        TIME NOT NULL
);

CREATE TABLE events
(
  eid         INTEGER AUTO_INCREMENT PRIMARY KEY,
  movie_id    INTEGER NOT NULL,
  date        DATE,
  seance_id   INTEGER,
  CONSTRAINT  seance_unique UNIQUE (seance_id, date),
  FOREIGN KEY (movie_id)    REFERENCES movies(mid) ON DELETE CASCADE,
  FOREIGN KEY (seance_id)   REFERENCES seances(sid) ON DELETE CASCADE
);

CREATE TABLE tickets
(
  tid         INTEGER AUTO_INCREMENT PRIMARY KEY,
  event_id    INTEGER NOT NULL,
  user_id     INTEGER,
  row         INTEGER,
  seat        INTEGER,
  CONSTRAINT  seat_unique UNIQUE (event_id, row, seat),
  FOREIGN KEY (event_id)  REFERENCES events(eid) ON DELETE CASCADE,
  FOREIGN KEY (user_id)   REFERENCES users(uid) ON DELETE CASCADE
);

INSERT INTO users (name, email, password, role) VALUES
  ('Ромашова Ольга', 'ro@gmail.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'ADMIN'),
  ('Тищенко Екатерина', 'te@gmail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'CLIENT'),
  ('Потапова Алеся', 'pa@gmail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'CLIENT'),
  ('Смирнова Ольга', 'so@gmail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'CLIENT'),
  ('Буланов Тимур', 'bt@gmail.com', 'fc613b4dfd6736a7bd268c8a0e74ed0d1c04a959f59dd74ef2874983fd443fc9', 'CLIENT');

INSERT INTO movies (name, genre, duration, year, active) VALUES
  ('Terminator', 'science-fiction action', 107, 1984, true),
  ('Titanic', 'epic romance', 195, 1997, true),
  ('Fifty Shades of Grey', 'erotic romantic drama', 125, 2015, true),
  ('Monsters, Inc.', 'computer-animated comedy', 92, 2001, true),
  ('Левиафан', 'драма', 142, 2014, true);

INSERT INTO seances (time) VALUES
  ('9:00:00'),
  ('13:00:00'),
  ('18:00:00'),
  ('22:00:00');

INSERT INTO events (movie_id, date, seance_id) VALUES
  (1, '2018-09-13', 2),
  (3, '2018-09-13', 4),
  (1, '2018-09-14', 2),
  (3, '2018-09-14', 4),
  (2, '2018-09-15', 3),
  (2, '2018-09-16', 3);

INSERT INTO tickets (event_id, user_id, row, seat) VALUES
  (2, 2, 4, 4),
  (2, 2, 4, 5),
  (2, 2, 4, 6),
  (2, 3, 3, 4),
  (2, 3, 3, 5),
  (2, 3, 3, 6),
  (1, 5, 4, 5),
  (1, 5, 4, 6);

