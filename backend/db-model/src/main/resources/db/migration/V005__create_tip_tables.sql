CREATE TABLE TIP
(
    TIP_ID    SERIAL PRIMARY KEY,
    USER_ID   INT NOT NULL,
    TIP_TEAM1 INT NOT NULL,
    TIP_TEAM2 INT NOT NULL,
    GAME_ID   INT NOT NULL,
    POINTS    INT NOT NULL,
    foreign key (USER_ID) references "user" (USER_ID),
    foreign key (GAME_ID) references GAME (GAME_ID)
);
