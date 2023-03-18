CREATE TABLE user (
    id         INT          NOT NULL AUTO_INCREMENT,
    username   VARCHAR(50)  NOT NULL,
    color      VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (username)
);
