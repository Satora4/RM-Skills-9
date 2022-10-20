INSERT INTO "user"(user_id, first_name, last_name, email, points, administrator)
VALUES (3, 'Ada', 'Lovelace', 'ada.lovelace@ergon.ch', 1815, TRUE),
       (4, 'Gertrude', 'Blanch', 'gertrude.blanch@ergon.ch', 1897, TRUE),
       (5, 'Rózsa', 'Péter', 'rózsa.péter@ergon.ch', 1905, TRUE)
ON CONFLICT DO NOTHING;

INSERT INTO tip(user_id, tip_team1, tip_team2, game_id)
VALUES (1, 2, 2, 1),
       (1, 2, 0, 2),
       (1, 0, 4, 3),
       (1, 3, 4, 4)
ON CONFLICT DO NOTHING;

INSERT INTO "group"(GROUP_ID, NAME)
VALUES (23, 'X'),
       (42, 'Y')
ON CONFLICT DO NOTHING;
