CREATE TABLE posts
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    date TIMESTAMP
);

CREATE TABLE cities
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
);

CREATE TABLE candidates
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    date TIMESTAMP,
    city_id INT REFERENCES cities (id)
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT UNIQUE,
    password TEXT
);
