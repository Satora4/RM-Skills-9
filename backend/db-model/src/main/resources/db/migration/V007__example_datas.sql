INSERT INTO "USER"(FIRST_NAME,
                   LAST_NAME,
                   EMAIL,
                   RANKING,
                   ADMINISTRATOR)
VALUES ('Niculin', 'Steiner', 'steiner.niculin@mail.ch', 2, true),
       ('Joel', 'Vontobel', 'joel.vontobel@ergon.ch', 3, true),
       ('Hans', 'Ruedi', 'hans.ruedi@mail.com', 2, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail2.com', 2, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail3.com', 2, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail4.com', 2, false),
       ('Hans', 'Ruedi', 'hans.ruedi@mail5.com', 2, false),
       ('Marc', 'Tremp', 'marc.tremp@mail.at', 15, false);



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
values ('2022-11-10T20:00:00', 'Katar', 2, 3, 9, 1),
       ('2022-11-10T22:00:00', 'Katar', 3, 4, 8, 1),
       ('2022-11-11T20:00:00', 'Katar', 0, 3, 1, 2),
       ('2022-11-11T22:00:00', 'Katar', 1, 4, 7, 2),
       ('2022-11-12T20:00:00', 'Katar', 2, 0, 9, 3),
       ('2022-11-12T22:00:00', 'Katar', 3, 1, 6, 3),
       ('2022-11-13T20:00:00', 'Katar', 2, 3, 8, 4),
       ('2022-11-13T22:00:00', 'Katar', 3, 4, 6, 4),
       ('2022-11-14T20:00:00', 'Katar', 5, 3, 7, 5),
       ('2022-11-14T22:00:00', 'Katar', 6, 4, 4, 5),
       ('2022-11-15T20:00:00', 'Katar', 2, 0, 5, 6),
       ('2022-11-15T22:00:00', 'Katar', 3, 1, 3, 6),
       ('2022-11-16T20:00:00', 'Katar', 3, 3, 5, 7),
       ('2022-11-16T22:00:00', 'Katar', 4, 4, 2, 7),
       ('2022-11-17T20:00:00', 'Katar', 2, 0, 4, 8),
       ('2022-11-17T22:00:00', 'Katar', 3, 1, 1, 8),
       ('2022-11-18T20:00:00', 'Katar', 2, 0, 3, 9),
       ('2022-11-18T22:00:00', 'Katar', 3, 1, 2, 9);



INSERT INTO TIP(USER_ID,
                TIP_TEAM1,
                TIP_TEAM2,
                GAME_ID,
                POINTS)
values (1, 2, 2, 8, 1),
       (2, 2, 0, 6, 2),
       (1, 0, 4, 1, 3),
       (4, 3, 4, 4, 2);



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
       (7, 2),
       (6, 3),
       (5, 3),
       (4, 4),
       (3, 4),
       (2, 2),
       (1, 4);
