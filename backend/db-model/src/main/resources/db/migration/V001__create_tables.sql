CREATE TABLE IF NOT EXISTS "user"
(
    user_id       SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email         VARCHAR(50) NOT NULL UNIQUE,
    points        INT         NOT NULL,
    administrator BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS team
(
    team_id SERIAL PRIMARY KEY,
    country VARCHAR(50) NOT NULL,
    points  INT         NOT NULL,
    flag    VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "group"
(
    group_id SERIAL PRIMARY KEY,
    name     CHAR NOT NULL
);

CREATE TYPE phase AS ENUM (
    'GROUP_PHASE',
    'ROUND_OF_16',
    'QUARTER_FINAL',
    'SEMI_FINAL',
    'LITTLE_FINAL',
    'FINAL'
    );

CREATE TABLE IF NOT EXISTS game
(
    game_id       SERIAL PRIMARY KEY,
    game_time     TIMESTAMP NOT NULL,
    game_location VARCHAR(50),
    team1_id      INT       NOT NULL,
    team2_id      INT       NOT NULL,
    goals_team1   INT,
    goals_team2   INT,
    phase         phase DEFAULT 'GROUP_PHASE',
    calculated    BOOLEAN,
    enableButtons BOOLEAN,
    foreign key (team1_id) references team (team_id),
    foreign key (team2_id) references team (team_id)
);

CREATE TABLE IF NOT EXISTS tip
(
    tip_id    SERIAL PRIMARY KEY,
    user_id   INT NOT NULL,
    tip_team1 INT NOT NULL,
    tip_team2 INT NOT NULL,
    game_id   INT NOT NULL,
    points    INT,

    foreign key (user_id) references "user" (user_id),
    foreign key (game_id) references game (game_id)
);

CREATE TABLE IF NOT EXISTS team_to_group
(
    team_id  INT UNIQUE NOT NULL,
    group_id INT        NOT NULL,

    foreign key (team_id) references team (team_id),
    foreign key (group_id) references "group" (group_id)
);
