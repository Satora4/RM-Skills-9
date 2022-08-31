INSERT INTO GAME (GAME_TIME,
                  GAME_LOCATION,
                  POINTS_TEAM1,
                  POINTS_TEAM2,
                  TEAM_ID1,
                  TEAM_ID2,
                  PHASE)
values ('2022-11-10T22:00:00', 'Katar', 0, 0, 12, 13, 'FINAL'),
       ('2022-11-10T22:00:00', 'Katar', 0, 0, 5, 8, 'SEMI_FINAL'),
       ('2022-11-10T22:00:00', 'Katar', 0, 0, 11, 10, 'QUARTER_FINAL');

UPDATE GAME
SET PHASE = 'ROUND_OF_16'
WHERE GAME_ID IN (8, 5, 12, 13);

UPDATE GAME
SET PHASE = 'QUARTER_FINAL'
WHERE GAME_ID IN (10, 11);
