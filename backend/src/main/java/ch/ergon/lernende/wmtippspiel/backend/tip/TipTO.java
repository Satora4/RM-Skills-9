package ch.ergon.lernende.wmtippspiel.backend.tip;

import java.time.LocalDateTime;

public class TipTO {
    private int id;
    //user
    private int userId;
    private int tipTeam1;
    private int tipTeam2;
    private String firstName;
    private String lastName;
    private String email;
    private int points;
    private int ranking;
    private boolean administrator;
    //game
    private int gameId;
    private LocalDateTime gameTime;
    private String gameLocation;
    private int pointsTeam1;
    private int pointsTeam2;
    private String teamCountry1;
    private String teamCountry2;
}
