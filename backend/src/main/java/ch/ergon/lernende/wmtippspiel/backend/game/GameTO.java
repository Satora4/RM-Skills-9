package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDate;
import java.util.List;

public class GameTO {

    private final List<Game> games;
    private final LocalDate groupDate;
    private final Phase phase;
    private final String groupName;

    private GameTO(List<Game> games, LocalDate groupDate, Phase phase, String groupName) {
        this.games = games;
        this.groupDate = groupDate;
        this.phase = phase;
        this.groupName = groupName;
    }

    public static GameTO gamesWithDate(List<Game> games, LocalDate playDate) {
        return new GameTO(games, playDate, null, null);
    }

    public static GameTO gamesWithKoPhases(List<Game> games, Phase phase) {
        return new GameTO(games, null, phase, null);
    }


    public static GameTO gamesWithGroup(List<Game> games, String groupName){
        return new GameTO(games, null,null,groupName);
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
