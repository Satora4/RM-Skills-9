package ch.ergon.lernende.wmtippspiel.backend.game;

import java.util.ArrayList;
import java.util.List;

public class GamesWithGroup {

    private String groupName;
    private List<Game> games;

    public GamesWithGroup() {
        games = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

        GamesWithGroup that = (GamesWithGroup) o;

        if (!groupName.equals(that.groupName)) return false;
        return games.equals(that.games);
    }

    @Override
    public int hashCode() {
        int result = groupName.hashCode();
        result = 31 * result + games.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GamesWithGroup{" +
                "groupName='" + groupName + '\'' +
                ", games=" + games +
                '}';
    }
}
