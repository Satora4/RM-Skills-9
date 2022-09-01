package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamToGroupTable;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;
import static org.jooq.impl.DSL.select;

@Repository
public class GameRepository {

    private static final TeamTable TEAM_ALIAS_1 = TEAM.as("t1");
    private static final TeamTable TEAM_ALIAS_2 = TEAM.as("t2");
    private static final TeamToGroupTable TEAM_TO_GROUP_ALIAS_1 = TEAM_TO_GROUP.as("ttg1");
    private static final TeamToGroupTable TEAM_TO_GROUP_ALIAS_2 = TEAM_TO_GROUP.as("ttg2");

    private final DSLContext dslContext;

    @Autowired
    public GameRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Game> getAllGames() {
        return getGamesWithCondition(DSL.noCondition());
    }

    public List<Game> getGamesForGroups() {
        return getGamesWithCondition(select(TEAM_TO_GROUP_ALIAS_1.GROUP_ID)
                .from(TEAM_TO_GROUP_ALIAS_1)
                .where(TEAM_ALIAS_1.TEAM_ID.eq(TEAM_TO_GROUP_ALIAS_1.TEAM_ID))
                .eq(select(TEAM_TO_GROUP_ALIAS_2.GROUP_ID)
                        .from(TEAM_TO_GROUP_ALIAS_2)
                        .where(TEAM_ALIAS_2.TEAM_ID.eq(TEAM_TO_GROUP_ALIAS_2.TEAM_ID))
                ));
    }

    public List<Game> getGamesForKoPhase() {
        return getGamesWithCondition(GAME.PHASE.notEqual(Phase.GROUP_PHASE));
    }

    /**
     * returns all games they're already done, means where the points aren't NULL
     */
    public List<Game> getGamesWithPoints() {
        return getGamesWithCondition(GAME.POINTS_TEAM1.isNotNull().and(GAME.POINTS_TEAM2.isNotNull()));
    }

    private List<Game> getGamesWithCondition(Condition condition) {
        return dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.POINTS_TEAM1,
                        GAME.POINTS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM_ID1))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM_ID2))
                .where(condition)
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
        game.setPhase(record.get(GAME.PHASE));

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
