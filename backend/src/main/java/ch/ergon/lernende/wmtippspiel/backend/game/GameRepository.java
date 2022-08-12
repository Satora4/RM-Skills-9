package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.TeamPhase;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamToGroupTable;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

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
        return condition(DSL.noCondition());
    }

    public List<Game> getGamesForGroups() {
        return condition(dslContext
                .select(TEAM_TO_GROUP_ALIAS_1.GROUP_ID)
                .from(TEAM_TO_GROUP_ALIAS_1)
                .where(TEAM_ALIAS_1.TEAM_ID.eq(TEAM_TO_GROUP_ALIAS_1.TEAM_ID))
                .eq(dslContext
                        .select(TEAM_TO_GROUP_ALIAS_2.GROUP_ID)
                        .from(TEAM_TO_GROUP_ALIAS_2)
                        .where(TEAM_ALIAS_2.TEAM_ID.eq(TEAM_TO_GROUP_ALIAS_2.TEAM_ID))
                ));
    }

    public List<Team> getGamesForKoPhase() {
        return dslContext
                .select(TEAM.COUNTRY, TEAM.TEAM_ID, TEAM.POINTS, TEAM.PHASE)
                .from(TEAM)
                .where(TEAM.PHASE.eq(TeamPhase.GROUP_PHASE))
                .fetch(this::convert);
    }

    private Team convert(Record4<String, Integer, Integer, TeamPhase> record4) {
        var team = new Team();

        team.setId(record4.get(TEAM.TEAM_ID));
        team.setCountry(record4.get(TEAM.COUNTRY));
        team.setPoints(record4.get(TEAM.POINTS));
        team.setPhase(record4.get(TEAM.PHASE));
        return team;
    }

    private List<Game> condition(Condition condition) {
        return dslContext.select(GAME.GAME_ID,
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
                .where(condition)
                .fetch(this::convert);
    }

    private Game convert(Record record) {
        var game = new Game();

        game.setId(record.get(GAME.GAME_ID));
        game.setGameTime(record.get(GAME.GAME_TIME));
        game.setGameLocation(record.get(GAME.GAME_LOCATION));
        game.setPointsTeam1(record.get(GAME.POINTS_TEAM1));
        game.setPointsTeam2(record.get(GAME.POINTS_TEAM2));

        var team1 = new Team();
        team1.setId(record.get(TEAM_ALIAS_1.TEAM_ID));
        team1.setCountry(record.get(TEAM_ALIAS_1.COUNTRY));
        game.setTeam1(team1);

        var team2 = new Team();
        team2.setId(record.get(TEAM_ALIAS_2.TEAM_ID));
        team2.setCountry(record.get(TEAM_ALIAS_2.COUNTRY));
        game.setTeam2(team2);
        return game;
    }
}
