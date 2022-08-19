package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.GAME;
import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.TEAM;

@Repository
public class GameRepository {

    private static final TeamTable TEAM_ALIAS_1 = TEAM.as("t1");
    private static final TeamTable TEAM_ALIAS_2 = TEAM.as("t2");

    private final DSLContext dslContext;

    @Autowired
    public GameRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Game> getAllGames() {
        List<Game> withOutPoints = dslContext
                .select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.POINTS_TEAM1,
                        GAME.POINTS_TEAM2,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM_ID1))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM_ID2))
                .fetch(this::convert);
        return withOutPoints;
    }

    /**
     * returns all games they're already done, means where the points aren't NULL
     */
    public List<Game> getGamesWithPoints() {
        return dslContext
                .select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.POINTS_TEAM1,
                        GAME.POINTS_TEAM2,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM_ID1))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM_ID2))
                .where(GAME.POINTS_TEAM1.isNotNull().and(GAME.POINTS_TEAM2.isNotNull()))
                .fetch(this::convert);
    }

    private Game convert(Record record) {
        Game game = new Game();

        game.setId(record.get(GAME.GAME_ID));
        game.setGameTime(record.get(GAME.GAME_TIME));
        game.setGameLocation(record.get(GAME.GAME_LOCATION));
        if (record.get(GAME.POINTS_TEAM1) != null) {
            game.setPointsTeam1(record.get(GAME.POINTS_TEAM1));
        }

        if (record.get(GAME.POINTS_TEAM2) != null) {
            game.setPointsTeam2(record.get(GAME.POINTS_TEAM2));
        }

        Team team1 = new Team();
        team1.setId(record.get(TEAM_ALIAS_1.TEAM_ID));
        team1.setCountry(record.get(TEAM_ALIAS_1.COUNTRY));
        game.setTeam1(team1);

        Team team2 = new Team();
        team2.setId(record.get(TEAM_ALIAS_2.TEAM_ID));
        team2.setCountry(record.get(TEAM_ALIAS_2.COUNTRY));
        game.setTeam2(team2);
        return game;
    }

}
