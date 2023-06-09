package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ch.ergon.lernende.wmtippspiel.backend.game.Games.*;
import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;
import static java.util.Comparator.comparing;
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

    public Game getGame(int gameId) {
        List<Game> games = getGamesWithCondition(GAME.GAME_ID.eq(gameId));
        if (games.size() == 1) {
            return games.get(0);
        } else {
            throw new IllegalArgumentException("No game found with id: " + gameId);
        }
    }

    public List<Games> getGamesForGroups() {
        var result = dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_1.FLAG,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY,
                        TEAM_ALIAS_2.FLAG,
                        GROUP.NAME,
                        GROUP.GROUP_ID)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .join(TEAM_TO_GROUP).on(TEAM_TO_GROUP.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(GROUP).on(GROUP.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .where(GAME.PHASE.eq(Phase.GROUP_PHASE))
                .collect(groupingBy(record -> record.get(GROUP.NAME), mapping(this::convert, toList())));

        return convertGroup(result);
    }

    public List<Games> getGamesInGroupPhaseWithOutGroupName() {
        var result = dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_1.FLAG,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY,
                        TEAM_ALIAS_2.FLAG)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .join(TEAM_TO_GROUP).on(TEAM_TO_GROUP.TEAM_ID.eq(GAME.TEAM1_ID))
                .where(GAME.PHASE.eq(Phase.GROUP_PHASE))
                .collect(groupingBy(record -> record.get(GAME.GAME_TIME).toLocalDate(), mapping(this::convert, toList())));

        return convertDate(result);
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
                        TEAM_ALIAS_1.FLAG,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY,
                        TEAM_ALIAS_2.FLAG)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .where(condition)
                .fetch(this::convert);
    }

    public List<Games> getGamesForKoPhase() {
        var result = dslContext.select(GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_1.FLAG,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY,
                        TEAM_ALIAS_2.FLAG)
                .from(GAME)
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .where(GAME.PHASE.notEqual(Phase.GROUP_PHASE))
                .collect(groupingBy(record -> record.get(GAME.PHASE), mapping(this::convert, toList())));

        return convertPhase(result);
    }

    /**
     * returns all games that already have been played, i.e. where the goals aren't NULL
     */

    public List<Game> getAllFinishedGamesWithOutCalculation() {
        return getGamesWithCondition(GAME.GOALS_TEAM1.isNotNull().and(GAME.GOALS_TEAM2.isNotNull()).and(GAME.CALCULATED.eq(false)));
    }

    public void markAsCalculated(Game game) {
        dslContext.update(GAME)
                .set(GAME.CALCULATED, true)
                .where(GAME.GAME_ID.eq(game.getId()))
                .execute();
    }

    private Game convert(Record record) {
        Game game = new Game();

        game.setId(record.get(GAME.GAME_ID));
        game.setGameTime(record.get(GAME.GAME_TIME));
        game.setGameLocation(record.get(GAME.GAME_LOCATION));
        if (record.get(GAME.GOALS_TEAM1) != null) {
            game.setGoalsTeam1(record.get(GAME.GOALS_TEAM1));
        }

        if (record.get(GAME.GOALS_TEAM2) != null) {
            game.setGoalsTeam2(record.get(GAME.GOALS_TEAM2));
        }
        game.setPhase(record.get(GAME.PHASE));
        game.setTeam1(createTeam(record, TEAM_ALIAS_1));
        game.setTeam2(createTeam(record, TEAM_ALIAS_2));
        return game;
    }

    private static Team createTeam(Record record, TeamTable teamAlias) {
        Team team = new Team();
        team.setId(record.get(teamAlias.TEAM_ID));
        team.setCountry(record.get(teamAlias.COUNTRY));
        team.setCountryFlag(record.get(teamAlias.FLAG));
        return team;
    }

    private static List<Games> convertGroup(Map<String, List<Game>> result) {
        return result.entrySet().stream()
                .map(record -> gamesWithGroup(sortedGames(record.getValue()), record.getKey()))
                .toList();
    }

    private static List<Games> convertDate(Map<LocalDate, List<Game>> result) {
        return result.entrySet().stream()
                .map(record -> gamesWithDate(sortedGames(record.getValue()), record.getKey()))
                .toList();
    }

    private static List<Games> convertPhase(Map<Phase, List<Game>> result) {
        return result.entrySet().stream()
                .map(record -> gamesWithKoPhases(sortedGames(record.getValue()), record.getKey()))
                .toList();
    }

    private static List<Game> sortedGames(List<Game> record) {
        return record.stream().sorted(comparing(Game::getGameTime)).toList();
    }
}
