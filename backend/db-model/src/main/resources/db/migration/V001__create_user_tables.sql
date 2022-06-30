CREATE TABLE IF NOT EXISTS USER
(
    USER_ID       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME    VARCHAR(50) NOT NULL,
    LAST_NAME     VARCHAR(50) NOT NULL,
    EMAIL         VARCHAR(50) NOT NULL UNIQUE,
    POINTS        INT         NOT NULL,
    RANKING       INT         NOT NULL,
    ADMINISTRATOR BOOLEAN     NOT NULL
);













