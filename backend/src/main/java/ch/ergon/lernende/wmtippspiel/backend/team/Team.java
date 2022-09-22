package ch.ergon.lernende.wmtippspiel.backend.team;

import java.util.Objects;

public class Team {

    private int id;
    private String country;
    private int points;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (points != team.points) return false;
        return Objects.equals(country, team.country);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + points;
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", points=" + points +
                '}';
    }
}
