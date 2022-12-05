insert into MEAL (ID, NAME)
values  (2, 'Burger'),
        (4, 'Pasta'),
        (5, 'Sandwich'),
        (3, 'Soup'),
        (1, 'Tea');

insert into MEAL_PRICE (ID, PRICE, MEAL_ID, MENU_ID)
values  (1, 10, 1, 1),
        (2, 15, 2, 1),
        (3, 25, 3, 2),
        (4, 15, 2, 2),
        (5, 15, 1, 3),
        (6, 25, 4, 3),
        (7, 15, 1, 4),
        (8, 25, 5, 4);

insert into MENU (ID, DATE_OF_MENU, RESTAURANT_ID)
values  (1, '2022-12-03', 1),
        (3, '2022-12-03', 2),
        (2, '2022-12-04', 1),
        (4, '2022-12-04', 2);

insert into RESTAURANT (ID, NAME)
values  (2, 'Aisha'),
        (1, 'Cherry');

insert into USER_ROLE (USER_ID, ROLE)
values  (1, 1),
        (2, 0);

insert into USERS (ID, EMAIL, ENABLED, NAME, PASSWORD, REGISTERED)
values  (1, 'admin@ya.ru', true, 'admin', 'admin', '2022-10-15 00:00:00.000000'),
        (2, 'user@gmail.com', true, 'user', 'user', '2022-10-20 00:00:00.000000');

insert into VOTE (ID, VOTE_DATE, VOTE_TIME, RESTAURANT_ID, USER_ID)
values  (1, '2022-12-05', '09:25:00', 2, 1),
        (2, '2022-12-05', '09:37:00', 1, 2);
        