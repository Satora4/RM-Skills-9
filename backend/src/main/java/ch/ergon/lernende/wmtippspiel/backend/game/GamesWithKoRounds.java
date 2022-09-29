package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamesWithKoRounds {

    private Phase phase;
    private List<Game> games;

    public GamesWithKoRounds() {
        games = new ArrayList<>();
    }

    public Phase getPhaseOfGames() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GamesWithKoRounds that = (GamesWithKoRounds) o;

        if (phase != that.phase) return false;
        return Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        int result = phase != null ? phase.hashCode() : 0;
        result = 31 * result + (games != null ? games.hashCode() : 0);
        return result;
    }
}
