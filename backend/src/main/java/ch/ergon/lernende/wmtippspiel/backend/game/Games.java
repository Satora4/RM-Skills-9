package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Games {
    private final List<Game> games;
    private final LocalDate playDate;
    private final Phase phase;
    private final String groupName;

    private Games(List<Game> games, LocalDate playDate, Phase phase, String groupName) {
        this.games = games;
        this.playDate = playDate;
        this.phase = phase;
        this.groupName = groupName;
    }

    public static Games gamesWithDate(List<Game> games, LocalDate playDate) {
        return new Games(games, playDate, null, null);
    }

    public static Games gamesWithKoPhases(List<Game> games, Phase phase) {
        return new Games(games, null, phase, null);
    }

    public static Games gamesWithGroup(List<Game> games, String groupName) {
        return new Games(games, null, null, groupName);
    }

    public List<Game> getGames() {
        return games;
    }

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(playDate);
    }

    public Optional<Phase> getPhase() {
        return Optional.ofNullable(phase);
    }

    public Optional<String> getGroupName() {
        return Optional.ofNullable(groupName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Games games1 = (Games) o;

        if (!games.equals(games1.games)) return false;
        if (!Objects.equals(playDate, games1.playDate)) return false;
        if (phase != games1.phase) return false;
        return Objects.equals(groupName, games1.groupName);
    }

    @Override
    public int hashCode() {
        int result = games.hashCode();
        result = 31 * result + (playDate != null ? playDate.hashCode() : 0);
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Games{" +
                "games=" + games +
                ", playDate=" + playDate +
                ", phase=" + phase +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
