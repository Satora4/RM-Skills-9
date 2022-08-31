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
VALUES (1, 'Switzerland', 12),
       (2, 'Brazil', 2),
       (3, 'Cameroon', 3),
       (4, 'England', 5),
       (5, 'Netherlands', 11),
       (6, 'Germany', 21),
       (7, 'France', 23),
       (8, 'Austria', 7),
       (9, 'Argentinian', 5),
       (10, 'Denmark', 6),
       (11, 'Iran', 9),
       (12, 'USA', 10),
       (13, 'Japan', 15);


INSERT INTO GAME(GAME_TIME,
                 GAME_LOCATION,
                 TEAM_ID1,
                 TEAM_ID2,
                 POINTS_TEAM1,
                 POINTS_TEAM2)
values ('2022-11-10T20:00:00', 'Katar', 9, 1, 2, 3),
       ('2022-11-10T22:00:00', 'Katar', 8, 1, 3, 4),
       ('2022-11-11T20:00:00', 'Katar', 1, 2, 0, 3),
       ('2022-11-11T22:00:00', 'Katar', 7, 2, 1, 4),
       ('2022-11-12T20:00:00', 'Katar', 9, 3, 2, 0),
       ('2022-11-12T22:00:00', 'Katar', 6, 3, 3, 1),
       ('2022-11-13T20:00:00', 'Katar', 8, 4, 2, 3),
       ('2022-11-13T22:00:00', 'Katar', 6, 4, 3, 4),
       ('2022-11-14T20:00:00', 'Katar', 7, 5, 5, 3),
       ('2022-11-14T22:00:00', 'Katar', 4, 5, DEFAULT, DEFAULT),
       ('2022-11-15T20:00:00', 'Katar', 5, 6, DEFAULT, DEFAULT),
       ('2022-11-15T22:00:00', 'Katar', 3, 6, DEFAULT, DEFAULT),
       ('2022-11-16T20:00:00', 'Katar', 5, 7, DEFAULT, DEFAULT),
       ('2022-11-16T22:00:00', 'Katar', 2, 7, DEFAULT, DEFAULT),
       ('2022-11-17T20:00:00', 'Katar', 4, 8, DEFAULT, DEFAULT),
       ('2022-11-17T22:00:00', 'Katar', 1, 8, DEFAULT, DEFAULT),
       ('2022-11-18T20:00:00', 'Katar', 3, 9, DEFAULT, DEFAULT),
       ('2022-11-18T22:00:00', 'Katar', 2, 9, DEFAULT, DEFAULT);



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
       (7, 2),
       (6, 3),
       (5, 3),
       (4, 4),
       (3, 4),
       (2, 6),
       (8, 7),
       (7, 8),
       (6, 8),
       (5, 7),
       (4, 6),
       (3, 5),
       (2, 5),
       (1, 6);
