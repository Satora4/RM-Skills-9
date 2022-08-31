package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.util.Objects;

public class Team {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (points != team.points) return false;
        if (!Objects.equals(country, team.country)) return false;
        return phase == team.phase;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + points;
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", points=" + points +
                ", phase=" + phase +
                '}';
    }
}
