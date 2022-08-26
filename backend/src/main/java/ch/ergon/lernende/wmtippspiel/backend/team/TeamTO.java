package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.TeamPhase;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamTO {

    @JsonProperty("teamId")
    private int id;
    private String country;
    private int points;
    private TeamPhase phase;

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

    public TeamPhase getPhase() {
        return phase;
    }

    public void setPhase(TeamPhase phase) {
        this.phase = phase;
    }
}
