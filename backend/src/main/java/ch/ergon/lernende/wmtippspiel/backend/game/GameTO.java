package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GameTO {

    private int id;
    private String groupName;
    private Phase phase;
    private LocalDate groupDate;
    private List<Game> games;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public LocalDate getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.groupDate = groupDate;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
