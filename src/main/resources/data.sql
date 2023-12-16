DELETE FROM user_role;
DELETE FROM dish;
DELETE FROM vote;
DELETE FROM restaurant;
DELETE FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');


INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurant (name)
VALUES ('Moscow time'),
       ('Meat place'),
       ('Yapona papa');

INSERT INTO dish (local_date, name, price, restaurant_id)
VALUES ('2023-12-10', 'Pizza', 480, 1),
       ('2023-12-10', 'Steak', 1990, 2),
       ('2023-12-10', 'Sushi set', 1299, 3),
       ('2023-12-11', 'Fish', 670, 1),
       ('2023-12-11', 'Fried chicken', 390, 2),
       ('2023-12-11', 'WOK', 450, 3);

INSERT INTO vote (local_date, user_id, restaurant_id)
VALUES ('2023-12-10', 1, 1),
       ('2023-12-10', 2, 1),
       ('2023-12-11', 1, 2),
       ('2023-12-12', 2, 3);
