DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR                 NOT NULL,
    email      VARCHAR                 NOT NULL,
    password   VARCHAR                 NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurant (name);

CREATE TABLE dish
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR            NOT NULL,
    price         DOUBLE PRECISION   NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    creating_date DATE DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dish_unique_restaurant_name_idx ON dish (name, creating_date, restaurant_id);

CREATE TABLE vote
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id       INTEGER            NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    voting_date    DATE DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_voting_date_idx ON vote (user_id, voting_date);