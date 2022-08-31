package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamTO {

    @JsonProperty("teamId")
    private int id;
    private String country;
    private int points;
    private Phase phase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }
}
