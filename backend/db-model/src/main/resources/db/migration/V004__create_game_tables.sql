CREATE TABLE GAME
(
    GAME_ID       INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    GAME_TIME     TIMESTAMP NOT NULL,
    GAME_LOCATION VARCHAR(50),
    POINTS_TEAM1  INT  NOT NULL,
    POINTS_TEAM2  INT  NOT NULL,

    TEAM_ID1      INT  NOT NULL,
    TEAM_ID2      INT  NOT NULL,
    foreign key (TEAM_ID1) references TEAM (TEAM_ID),

    foreign key (TEAM_ID2) references TEAM (TEAM_ID)
);