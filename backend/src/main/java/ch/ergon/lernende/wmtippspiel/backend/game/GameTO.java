package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDateTime;

public class GameTO {

    private int id;
    private LocalDateTime gameTime;
    private String gameLocation;
    private Integer pointsTeam1;
    private Integer pointsTeam2;
    private String teamCountry1;
    private String teamCountry2;
    private String flagTeam1;
    private String flagTeam2;
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

    public String getTeamCountry1() {
        return teamCountry1;
    }

    public void setTeamCountry1(String teamCountry1) {
        this.teamCountry1 = teamCountry1;
    }

    public String getTeamCountry2() {
        return teamCountry2;
    }

    public void setTeamCountry2(String teamCountry2) {
        this.teamCountry2 = teamCountry2;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getFlagTeam1() {
        return flagTeam1;
    }

    public void setFlagTeam1(String flagTeam1) {
        this.flagTeam1 = flagTeam1;
    }

    public String getFlagTeam2() {
        return flagTeam2;
    }

    public void setFlagTeam2(String flagTeam2) {
        this.flagTeam2 = flagTeam2;
    }
}
