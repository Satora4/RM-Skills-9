package ch.ergon.lernende.wmtippspiel.backend.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamesWithDate implements Comparable<LocalDate> {

    private LocalDate date;
    private List<Game> games;

    public GamesWithDate() {
        games = new ArrayList<>();
    }

    public LocalDate getGroupDate() {
        return date;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.date = groupDate;
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

        GamesWithDate that = (GamesWithDate) o;

        if (!Objects.equals(date, that.date)) return false;
        return Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (games != null ? games.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(LocalDate o) {
        return this.date.compareTo(o);
    }
}
