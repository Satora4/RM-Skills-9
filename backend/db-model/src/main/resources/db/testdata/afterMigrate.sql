INSERT INTO "user"(user_id, first_name, last_name, email, points, administrator)
VALUES (3, 'Ada', 'Lovelace', 'ada.lovelace@ergon.ch', 1815, TRUE),
       (4, 'Gertrude', 'Blanch', 'gertrude.blanch@ergon.ch', 1897, TRUE),
       (5, 'Rózsa', 'Péter', 'rózsa.péter@ergon.ch', 1905, TRUE)
ON CONFLICT DO NOTHING;
