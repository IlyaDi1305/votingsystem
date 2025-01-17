INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name)
VALUES ('McDonalds'),
       ('Pizzeria Bacco'),
       ('Taj im Taunus');

INSERT INTO MENU_ITEM (name, description, price, menu_item_date, restaurant_id)
VALUES ('Burger', 'brotchen, fleisch', 755, now(), 1),
       ('Cola', 'zuker, wasser', 415, now(), 1),
       ('Pizza Salami', 'salami', 820, now(), 2),
       ('Misosup', 'wasser, soya', 1290, now(), 3),
       ('Burger', 'brotchen, fleisch', 755, DATEADD(DAY, 1, NOW()), 1),
       ('Cola', 'zuker, wasser', 415, DATEADD(DAY, 1, NOW()), 1),
       ('Pizza Salami', 'salami', 820, DATEADD(DAY, 1, NOW()), 2),
       ('Misosup', 'wasser, soya', 1290, DATEADD(DAY, 1, NOW()), 3);

INSERT INTO VOTE (vote_date, vote_time, user_id, restaurant_id)
VALUES (now(), '7:30', 1, 1),
       (now(), '9:30', 2, 2),
       (now(), '11:30', 3, 3);