CREATE TYPE PHASE AS ENUM (
    'GROUP_PHASE',
    'ROUND_OF_16',
    'QUARTER_FINAL',
    'SEMI_FINAL',
    'FINAL'
    );

CREATE TABLE GAME
(
    GAME_ID       SERIAL PRIMARY KEY,
    GAME_TIME     TIMESTAMP NOT NULL,
    GAME_LOCATION VARCHAR(50),
    TEAM1_ID      INT       NOT NULL,
    TEAM2_ID      INT       NOT NULL,
    GOALS_TEAM1   INT,
    GOALS_TEAM2   INT,
    PHASE         PHASE DEFAULT 'GROUP_PHASE',
    CALCULATED    BOOLEAN,
    foreign key (TEAM1_ID) references TEAM (TEAM_ID),
    foreign key (TEAM2_ID) references TEAM (TEAM_ID)
);
