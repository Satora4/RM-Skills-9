INSERT INTO USER(FIRST_NAME,
                 LAST_NAME,
                 EMAIL,
                 POINTS,
                 RANKING,
                 ADMINISTRATOR)
VALUES ('Niculin', 'Steiner', 'steiner.niculin@mail.ch', 12, 2, true),
       ('Joel', 'Vontobel', 'joel.vontobel@ergon.ch', 11, 3, true),
       ('Hans', 'Ruedi', 'hans.ruedi@mail.com', 2, 4, false),
       ('Marc', 'Tremp', 'marc.tremp@mail.at', 15, 1, false);



INSERT INTO TEAM(COUNTRY,
                 POINTS)
VALUES ('Switzerland', 9),
       ('Brazil', 9),
       ('Cameroon', 9),
       ('England', 9),
       ('Netherlands', 9),
       ('Germany', 9),
       ('France', 9),
       ('Austria', 9),
       ('Argentinian', 9),
       ('Denmark', 9),
       ('Iran', 9),
       ('USA', 9),
       ('Japan', 9);


INSERT INTO GAME(GAME_TIME,
                 GAME_LOCATION,
                 POINTS_TEAM1,
                 POINTS_TEAM2,
                 TEAM_ID1,
                 TEAM_ID2)
values ('2022-11-12', 'Katar', 2, 3, 9, 1),
       ('2022-11-12', 'Katar', 0, 3, 8, 2),
       ('2022-11-12', 'Katar', 2, 0, 7, 3),
       ('2022-11-12', 'Katar', 2, 3, 6, 4),
       ('2022-11-12', 'Katar', 5, 3, 5, 5),
       ('2022-11-12', 'Katar', 2, 0, 4, 6),
       ('2022-11-12', 'Katar', 3, 3, 3, 7),
       ('2022-11-12', 'Katar', 2, 0, 2, 8),
       ('2022-11-12', 'Katar', 2, 0, 1, 9);



INSERT INTO TIP(USER_ID,
                TIP_TEAM1,
                TIP_TEAM2,
                GAME_ID)
values (1, 2, 2, 8),
       (2, 2, 0, 6),
       (1, 0, 4, 1),
       (4, 3, 4, 4);



INSERT INTO GROUP(NAME)
values ('A'),
       ('B'),
       ('C'),
       ('D'),
       ('E'),
       ('F'),
       ('G'),
       ('H');



INSERT INTO TEAM_TO_GROUP(TEAM_ID,
                          GROUP_ID)
values (9, SELECT GROUP_ID from GROUP where NAME = 'C'),
       (8, SELECT GROUP_ID from GROUP where NAME = 'B'),
       (7, SELECT GROUP_ID from GROUP where NAME = 'D'),
       (6, SELECT GROUP_ID from GROUP where NAME = 'A');
