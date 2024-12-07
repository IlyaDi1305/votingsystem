INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO RESTAURANTS (name)
VALUES ('Rest1'),
       ('Rest2'),
       ('Rest3');

INSERT INTO MENUS (name, description, price, date, restaurant_id)
VALUES ('Menu1', 'Eda1', 11.50, now(), 1),
       ('Menu2', 'Eda2', 22.50, now(),2),
       ('Menu2.2', 'Eda2.2', 22.50, now(),2),
       ('Menu3', 'Eda3', 33.50, now(),3);

INSERT INTO VOTES (date, vote_time, user_id, restaurant_id)
VALUES (now(), '09:00', 1,  1),
       (now(), '09:00', 2,2),
       (now(), '09:00', 3,3);
