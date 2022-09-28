DELETE
FROM TEAM_TO_GROUP;

DELETE
FROM "GROUP";

DELETE
FROM TIP;

DELETE
FROM GAME;

DELETE
FROM TEAM;

INSERT INTO TEAM(TEAM_ID,
                 COUNTRY,
                 POINTS)
VALUES (1, 'Argentinien', 0),
       (2, 'Australien', 0),
       (3, 'Belgien', 0),
       (4, 'Brasilien', 0),
       (5, 'Costa Rica', 0),
       (6, 'Dänemark', 0),
       (7, 'Deutschland', 0),
       (8, 'Ecuador', 0),
       (9, 'England', 0),
       (10, 'Frankreich', 0),
       (11, 'Ghana', 0),
       (12, 'Iran', 0),
       (13, 'Japan', 0),
       (14, 'Kamerun', 0),
       (15, 'Kanada', 0),
       (16, 'Katar', 0),
       (17, 'Korea Republik', 0),
       (18, 'Kroatien', 0),
       (19, 'Marokko', 0),
       (20, 'Mexiko', 0),
       (21, 'Niederlande', 0),
       (22, 'Polen', 0),
       (23, 'Portugal', 0),
       (24, 'Saudi Arabien', 0),
       (25, 'Schweiz', 0),
       (26, 'Senegal', 0),
       (27, 'Serbien', 0),
       (28, 'Spanien', 0),
       (29, 'Tunesien', 0),
       (30, 'Uruguay', 0),
       (31, 'USA', 0),
       (32, 'Wales', 0);

INSERT INTO "GROUP"(GROUP_ID, NAME)
VALUES (1, 'A'),
       (2, 'B'),
       (3, 'C'),
       (4, 'D'),
       (5, 'E'),
       (6, 'F'),
       (7, 'G'),
       (8, 'H');

INSERT INTO TEAM_TO_GROUP(TEAM_ID,
                          GROUP_ID)
VALUES ('16', '1'),
       ('8', '1'),
       ('26', '1'),
       ('21', '1'),
       ('9', '2'),
       ('12', '2'),
       ('31', '2'),
       ('32', '2'),
       ('1', '3'),
       ('24', '3'),
       ('20', '3'),
       ('22', '3'),
       ('10', '4'),
       ('6', '4'),
       ('29', '4'),
       ('2', '4'),
       ('28', '5'),
       ('7', '5'),
       ('13', '5'),
       ('5', '5'),
       ('3', '6'),
       ('15', '6'),
       ('19', '6'),
       ('18', '6'),
       ('4', '7'),
       ('27', '7'),
       ('25', '7'),
       ('14', '7'),
       ('23', '8'),
       ('11', '8'),
       ('30', '8'),
       ('17', '8');



INSERT INTO GAME(GAME_ID,
                 GAME_TIME,
                 GAME_LOCATION,
                 TEAM1_ID,
                 TEAM2_ID,
                 PHASE)
VALUES (1, '2022-11-20T17:00:00', 'Katar', 16, 8, 'GROUP_PHASE'),
       (2, '2022-11-21T14:00:00', 'Katar', 9, 12, 'GROUP_PHASE'),
       (3, '2022-11-21T17:00:00', 'Katar', 26, 21, 'GROUP_PHASE'),
       (4, '2022-11-21T20:00:00', 'Katar', 31, 32, 'GROUP_PHASE'),
       (5, '2022-11-22T11:00:00', 'Katar', 1, 24, 'GROUP_PHASE'),
       (6, '2022-11-22T14:00:00', 'Katar', 6, 29, 'GROUP_PHASE'),
       (7, '2022-11-22T17:00:00', 'Katar', 20, 22, 'GROUP_PHASE'),
       (8, '2022-11-22T20:00:00', 'Katar', 10, 2, 'GROUP_PHASE'),
       (9, '2022-11-23T11:00:00', 'Katar', 19, 18, 'GROUP_PHASE'),
       (10, '2022-11-23T14:00:00', 'Katar', 7, 13, 'GROUP_PHASE'),
       (11, '2022-11-23T17:00:00', 'Katar', 28, 5, 'GROUP_PHASE'),
       (12, '2022-11-23T20:00:00', 'Katar', 3, 15, 'GROUP_PHASE'),
       (13, '2022-11-24T11:00:00', 'Katar', 25, 14, 'GROUP_PHASE'),
       (14, '2022-11-24T14:00:00', 'Katar', 30, 17, 'GROUP_PHASE'),
       (15, '2022-11-24T17:00:00', 'Katar', 23, 11, 'GROUP_PHASE'),
       (16, '2022-11-24T20:00:00', 'Katar', 4, 27, 'GROUP_PHASE'),
       (17, '2022-11-25T11:00:00', 'Katar', 32, 12, 'GROUP_PHASE'),
       (18, '2022-11-25T14:00:00', 'Katar', 16, 26, 'GROUP_PHASE'),
       (19, '2022-11-25T17:00:00', 'Katar', 21, 8, 'GROUP_PHASE'),
       (20, '2022-11-25T20:00:00', 'Katar', 9, 31, 'GROUP_PHASE'),
       (21, '2022-11-26T11:00:00', 'Katar', 29, 2, 'GROUP_PHASE'),
       (22, '2022-11-26T14:00:00', 'Katar', 22, 24, 'GROUP_PHASE'),
       (23, '2022-11-26T17:00:00', 'Katar', 10, 6, 'GROUP_PHASE'),
       (24, '2022-11-26T20:00:00', 'Katar', 1, 20, 'GROUP_PHASE'),
       (25, '2022-11-27T11:00:00', 'Katar', 13, 5, 'GROUP_PHASE'),
       (26, '2022-11-27T14:00:00', 'Katar', 3, 19, 'GROUP_PHASE'),
       (27, '2022-11-27T17:00:00', 'Katar', 18, 15, 'GROUP_PHASE'),
       (28, '2022-11-27T20:00:00', 'Katar', 28, 7, 'GROUP_PHASE'),
       (29, '2022-11-28T11:00:00', 'Katar', 14, 27, 'GROUP_PHASE'),
       (30, '2022-11-28T14:00:00', 'Katar', 17, 11, 'GROUP_PHASE'),
       (31, '2022-11-28T17:00:00', 'Katar', 4, 25, 'GROUP_PHASE'),
       (32, '2022-11-28T20:00:00', 'Katar', 23, 30, 'GROUP_PHASE'),
       (33, '2022-11-29T11:00:00', 'Katar', 21, 16, 'GROUP_PHASE'),
       (34, '2022-11-29T14:00:00', 'Katar', 8, 26, 'GROUP_PHASE'),
       (35, '2022-11-29T17:00:00', 'Katar', 32, 9, 'GROUP_PHASE'),
       (36, '2022-11-29T20:00:00', 'Katar', 12, 31, 'GROUP_PHASE'),
       (37, '2022-11-30T11:00:00', 'Katar', 2, 6, 'GROUP_PHASE'),
       (38, '2022-11-30T14:00:00', 'Katar', 29, 10, 'GROUP_PHASE'),
       (39, '2022-11-30T17:00:00', 'Katar', 22, 1, 'GROUP_PHASE'),
       (40, '2022-11-30T20:00:00', 'Katar', 24, 20, 'GROUP_PHASE'),
       (41, '2022-12-01T11:00:00', 'Katar', 18, 3, 'GROUP_PHASE'),
       (42, '2022-12-01T14:00:00', 'Katar', 15, 19, 'GROUP_PHASE'),
       (43, '2022-12-01T17:00:00', 'Katar', 13, 28, 'GROUP_PHASE'),
       (44, '2022-12-01T20:00:00', 'Katar', 5, 7, 'GROUP_PHASE'),
       (45, '2022-12-02T11:00:00', 'Katar', 11, 30, 'GROUP_PHASE'),
       (46, '2022-12-02T14:00:00', 'Katar', 17, 23, 'GROUP_PHASE'),
       (47, '2022-12-02T17:00:00', 'Katar', 27, 25, 'GROUP_PHASE'),
       (48, '2022-12-02T20:00:00', 'Katar', 14, 4, 'GROUP_PHASE');

INSERT INTO "USER"(USER_ID,
                   FIRST_NAME,
                   LAST_NAME,
                   EMAIL,
                   POINTS,
                   ADMINISTRATOR)
VALUES (1, 'Joel', 'Vontobel', 'joel.vontobel@ergon.ch', 780, TRUE),
       (2, 'Niculin', 'Steiner', 'niculin.steiner@ergon.ch', 34, TRUE);

INSERT INTO TIP(USER_ID,
                TIP_TEAM1,
                TIP_TEAM2,
                GAME_ID)
VALUES (1, 2, 2, 1),
       (1, 2, 0, 2),
       (1, 0, 4, 3),
       (1, 3, 4, 4);