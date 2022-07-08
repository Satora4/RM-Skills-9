package ch.ergon.lernende.wmtippspiel.backend.team;

public class Team {
    private int teamId;
    private String country;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return teamId;
    }

    public void setId(int id) {
        this.teamId = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
