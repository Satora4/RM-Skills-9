CREATE TABLE IF NOT EXISTS GAME
(
    GAME_ID       INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    GAME_TIME     DATE NOT NULL,
    GAME_LOCATION VARCHAR(50),
    POINTS_TEAM1  INT  NOT NULL,
    POINTS_TEAM2  INT  NOT NULL,

    fk_Team1      INT  NOT NULL,
    fk_Team2      INT  NOT NULL,
    foreign key (fk_Team1) references TEAM(TEAM_ID),

    foreign key (fk_Team2) references TEAM(TEAM_ID)
    );