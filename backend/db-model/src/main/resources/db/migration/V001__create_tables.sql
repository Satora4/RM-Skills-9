CREATE TABLE IF NOT EXISTS "user"
(
    user_id       SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL UNIQUE,
    points        INT         NOT NULL,
    administrator BOOLEAN     NOT NULL
);
