CREATE TABLE IF NOT EXISTS TEAM_TO_GROUP
(
    TEAM_ID  INT NOT NULL,
    GROUP_ID INT NOT NULL,
    foreign key (TEAM_ID) references TEAM (TEAM_ID),
    foreign key (GROUP_ID) references "GROUP" (GROUP_ID)
);