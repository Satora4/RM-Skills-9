package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDate;
import java.util.List;

public class GamesTO {

    private final List<Game> games;
    private final LocalDate groupDate;
    private final Phase phase;
    private final String groupName;

    private GamesTO(List<Game> games, LocalDate groupDate, Phase phase, String groupName) {
        this.games = games;
        this.groupDate = groupDate;
        this.phase = phase;
        this.groupName = groupName;
    }

    public static GamesTO gamesWithDate(List<Game> games, LocalDate playDate) {
        return new GamesTO(games, playDate, null, null);
    }

    public static GamesTO gamesWithKoPhases(List<Game> games, Phase phase) {
        return new GamesTO(games, null, phase, null);
    }

    public static GamesTO gamesWithGroup(List<Game> games, String groupName) {
        return new GamesTO(games, null, null, groupName);
    }

    public List<Game> getGames() {
        return games;
    }

    public LocalDate getGroupDate() {
        return groupDate;
    }

    public Phase getPhase() {
        return phase;
    }

    public String getGroupName() {
        return groupName;
    }
}
