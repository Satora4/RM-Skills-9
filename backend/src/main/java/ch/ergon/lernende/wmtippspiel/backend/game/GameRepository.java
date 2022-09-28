package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.tip.TipTO;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;
import static java.util.stream.Collectors.*;

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
        return getGamesWithCondition(DSL.noCondition());
    }

    public List<GamesWithGroup> getGamesForGroups() {
        var result = dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY,
                        GROUP.NAME,
                        GROUP.GROUP_ID)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .join(TEAM_TO_GROUP).on(TEAM_TO_GROUP.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(GROUP).on(GROUP.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .where(GAME.PHASE.eq(Phase.GROUP_PHASE))
                .collect(groupingBy(this::convertToGroup, mapping(this::convertToGames, toList())));

        return convert(result);
    }

    private GamesWithGroup convertToGroup(Record record) {
        GamesWithGroup gamesWithGroup = new GamesWithGroup();

        gamesWithGroup.setGroupName(record.get(GROUP.NAME));
        return gamesWithGroup;
    }

    private Game convertToGames(Record record) {
        return convert(record);
    }

    private List<GamesWithGroup> convert(Map<GamesWithGroup, List<Game>> result) {
        List<GamesWithGroup> gamesWithGroups = new ArrayList<>();
        for (var record : result.entrySet()) {
            GamesWithGroup gamesWithGroup = record.getKey();
            gamesWithGroup.setGames(record.getValue());
            gamesWithGroups.add(gamesWithGroup);
        }
        return gamesWithGroups;
    }

    public List<Game> getGamesForKoPhase() {
        return getGamesWithCondition(GAME.PHASE.notEqual(Phase.GROUP_PHASE));
    }

    /**
     * returns all games they're already done, means where the points aren't NULL
     */
    public List<Game> getGamesWithPoints() {
        return getGamesWithCondition(GAME.GOALS_TEAM1.isNotNull().and(GAME.GOALS_TEAM2.isNotNull()));
    }

    private List<Game> getGamesWithCondition(Condition condition) {
        return dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .where(condition)
                .fetch(this::convert);
    }

    public LocalDateTime getGameTime(TipTO tipTO) {
        return dslContext.select(GAME.GAME_TIME)
                .from(GAME)
                .join(TIP).on(TIP.GAME_ID.eq(GAME.GAME_ID))
                .where(GAME.GAME_ID.eq(tipTO.getGameId()))
                .fetchOne(this::convertGameTime);
    }

    private Game convert(Record record) {
        Game game = new Game();

        game.setId(record.get(GAME.GAME_ID));
        game.setGameTime(record.get(GAME.GAME_TIME));
        game.setGameLocation(record.get(GAME.GAME_LOCATION));
        if (record.get(GAME.GOALS_TEAM1) != null) {
            game.setPointsTeam1(record.get(GAME.GOALS_TEAM1));
        }

        if (record.get(GAME.GOALS_TEAM2) != null) {
            game.setPointsTeam2(record.get(GAME.GOALS_TEAM2));
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

    private LocalDateTime convertGameTime(Record record) {
        LocalDateTime gameTime;
        gameTime = record.get(GAME.GAME_TIME);

        return gameTime;
    }
}
