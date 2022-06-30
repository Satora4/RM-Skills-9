CREATE TABLE IF NOT EXISTS GROUPE
(
    TEAM1 INT NOT NULL,
    TEAM2 INT NOT NULL,
    TEAM3 INT NOT NULL,
    TEAM4 INT NOT NULL,
    foreign key (TEAM1) references TEAM(TEAM_ID),
    foreign key (TEAM2) references TEAM(TEAM_ID),
    foreign key (TEAM3) references TEAM(TEAM_ID),
    foreign key (TEAM4) references TEAM(TEAM_ID)
);