package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.util.ArrayList;
import java.util.List;

public class GamesWithKoRoundsTO {
    private String phase;
    private List<Game> games;

    public GamesWithKoRoundsTO() {
        games = new ArrayList<>();
    }

    public String getPhaseOfGames() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

}
