package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDateTime;
import java.util.Objects;

public class Game implements Comparable<LocalDateTime> {

    private int id;
    private LocalDateTime gameTime;
    private String gameLocation;
    private Integer pointsTeam1;
    private Integer pointsTeam2;
    private Team team1;
    private Team team2;
    private Phase phase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    public void setGameTime(LocalDateTime gameTime) {
        this.gameTime = gameTime;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public void setGameLocation(String gameLocation) {
        this.gameLocation = gameLocation;
    }

    public Integer getPointsTeam1() {
        return pointsTeam1;
    }

    public void setPointsTeam1(Integer pointsTeam1) {
        this.pointsTeam1 = pointsTeam1;
    }

    public Integer getPointsTeam2() {
        return pointsTeam2;
    }

    public void setPointsTeam2(Integer pointsTeam2) {
        this.pointsTeam2 = pointsTeam2;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
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

        Game game = (Game) o;

        if (id != game.id) return false;
        if (!Objects.equals(gameTime, game.gameTime)) return false;
        if (!gameLocation.equals(game.gameLocation)) return false;
        if (!pointsTeam1.equals(game.pointsTeam1)) return false;
        if (!pointsTeam2.equals(game.pointsTeam2)) return false;
        if (!Objects.equals(team1, game.team1)) return false;
        if (!Objects.equals(team2, game.team2)) return false;
        return phase == game.phase;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gameTime != null ? gameTime.hashCode() : 0);
        result = 31 * result + gameLocation.hashCode();
        result = 31 * result + pointsTeam1.hashCode();
        result = 31 * result + pointsTeam2.hashCode();
        result = 31 * result + (team1 != null ? team1.hashCode() : 0);
        result = 31 * result + (team2 != null ? team2.hashCode() : 0);
        result = 31 * result + (phase != null ? phase.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameTime=" + gameTime +
                ", gameLocation='" + gameLocation + '\'' +
                ", pointsTeam1=" + pointsTeam1 +
                ", pointsTeam2=" + pointsTeam2 +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", phase=" + phase +
                '}';
    }

    @Override
    public int compareTo(LocalDateTime o) {
        return this.gameTime.compareTo(o);
    }
}
