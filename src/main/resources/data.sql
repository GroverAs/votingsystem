DELETE FROM user_role;
DELETE FROM dish;
DELETE FROM vote;
DELETE FROM restaurant;
DELETE FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');


INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO restaurant (name)
VALUES ('Moscow time'),
       ('Meat place'),
       ('Yapona papa');

INSERT INTO dish (name, price, restaurant_id, creating_date)
VALUES ('Pizza', 480, 1, CURRENT_DATE),
       ('Soup', 350, 1, CURRENT_DATE),
       ('Coffee', 120, 1, CURRENT_DATE),
       ('Steak', 1990, 2, CURRENT_DATE),
       ('Burger', 290, 2, CURRENT_DATE),
       ('Tea', 95, 2, CURRENT_DATE),
       ('Sushi set', 1299, 3, CURRENT_DATE),
       ('Fish', 670, 3, CURRENT_DATE),
       ('WOK', 450, 3, CURRENT_DATE);

INSERT INTO vote (user_id, restaurant_id, local_date)
VALUES (1, 1, CURRENT_DATE),
       (2, 1, CURRENT_DATE),
       (3, 3, CURRENT_DATE);

