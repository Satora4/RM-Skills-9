package ch.ergon.lernende.wmtippspiel.backend.game;

import java.util.List;

public class GamesWithGroupTO {

    private String groupName;
    private List<Game> games;

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
}
