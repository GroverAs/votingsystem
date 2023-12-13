DELETE FROM users;
DELETE FROM user_role;
DELETE FROM dish;
DELETE FROM vote;
DELETE FROM restaurant;

INSERT INTO users (name, email, password)
VALUES ('User1', 'user1@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password'),
       ('User4', 'user4@yandex.ru', '{noop}password');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO restaurant (name)
VALUES ('Moscow time'),
       ('Meat place'),
       ('Yapona papa');

INSERT INTO dish (name, price, restaurant_id)
VALUES ('Pizza', 480, 1),
       ('Steak', 890, 2),
       ('Sushi set', 1299, 3);

INSERT INTO vote (user_id, restaurant_id, vote_time)
VALUES (1, 1,'2023-12-10'),
       (2, 1,'2023-12-10'),
       (3, 2,'2023-12-10'),
       (4, 3,'2023-12-10');
