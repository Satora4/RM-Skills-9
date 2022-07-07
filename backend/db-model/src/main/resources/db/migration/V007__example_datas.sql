INSERT INTO "USER"(FIRST_NAME,
                   LAST_NAME,
                   EMAIL,
                   POINTS,
                   RANKING,
                   ADMINISTRATOR)
VALUES ('Niculin', 'Steiner', 'steiner.niculin@mail.ch', 12, 2, true),
       ('Joel', 'Vontobel', 'joel.vontobel@ergon.ch', 11, 3, true),
       ('Hans', 'Ruedi', 'hans.ruedi@mail.com', 2, 4, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail2.com', 2, 6, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail3.com', 2, 5, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail4.com', 2, 8, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail5.com', 2, 7, false),
       ('Marc', 'Tremp', 'marc.tremp@mail.at', 15, 1, false);



INSERT INTO TEAM(TEAM_ID,
                 COUNTRY,
                 POINTS)
VALUES (1, 'Switzerland', 9),
       (2, 'Brazil', 9),
       (3, 'Cameroon', 9),
       (4, 'England', 9),
       (5, 'Netherlands', 9),
       (6, 'Germany', 9),
       (7, 'France', 9),
       (8, 'Austria', 9),
       (9, 'Argentinian', 9),
       (10, 'Denmark', 9),
       (11, 'Iran', 9),
       (12, 'USA', 9),
       (13, 'Japan', 9);


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



INSERT INTO "GROUP"(NAME)
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
values (9, 1),
       (8, 2),
       (7, 3),
       (6, 4);
