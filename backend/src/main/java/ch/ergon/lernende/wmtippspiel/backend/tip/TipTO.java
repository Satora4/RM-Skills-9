package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;

import java.time.LocalDateTime;

public class TipTO {
    private int id;
    private int tipTeam1;
    private int tipTeam2;
    private Integer points;
    //user
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private int userPoints;
    //game
    private int gameId;
    private LocalDateTime gameTime;
    private String gameLocation;
    private int teamId1;
    private int teamId2;
    private Integer goalsTeam1;
    private Integer goalsTeam2;
    private String teamCountry1;
    private String teamCountry2;
    private Phase phase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipTeam1() {
        return tipTeam1;
    }

    public void setTipTeam1(int tipTeam1) {
        this.tipTeam1 = tipTeam1;
    }

    public int getTipTeam2() {
        return tipTeam2;
    }

    public void setTipTeam2(int tipTeam2) {
        this.tipTeam2 = tipTeam2;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public int getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(int teamId1) {
        this.teamId1 = teamId1;
    }

    public int getTeamId2() {
        return teamId2;
    }

    public void setTeamId2(int teamId2) {
        this.teamId2 = teamId2;
    }

    public Integer getGoalsTeam1() {
        return goalsTeam1;
    }

    public void setGoalsTeam1(Integer goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
    }

    public Integer getGoalsTeam2() {
        return goalsTeam2;
    }

    public void setGoalsTeam2(Integer goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;

    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }
}