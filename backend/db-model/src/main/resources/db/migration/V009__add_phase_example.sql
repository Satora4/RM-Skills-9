INSERT INTO GAME (GAME_TIME,
                  GAME_LOCATION,
                  POINTS_TEAM1,
                  POINTS_TEAM2,
                  TEAM_ID1,
                  TEAM_ID2)
values ('2022-11-10T22:00:00', 'Katar', 0, 0, 12, 13),
       ('2022-11-10T22:00:00', 'Katar', 0, 0, 5, 8),
       ('2022-11-10T22:00:00', 'Katar', 0, 0, 11, 10);

UPDATE TEAM
SET PHASE = 'ROUND_OF_16'
WHERE TEAM_ID IN (8, 5, 12, 13);

UPDATE TEAM
SET PHASE = 'QUARTER_FiNAL'
WHERE TEAM_ID IN (10, 11);
