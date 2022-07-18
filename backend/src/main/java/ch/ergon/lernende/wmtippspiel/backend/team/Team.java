package ch.ergon.lernende.wmtippspiel.backend.team;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {

    @JsonProperty("teamId")
    private int id;
    private String country;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

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
}
