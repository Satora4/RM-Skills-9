package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.user.User;

public class Tip {
    private int id;
    private User user;
    private int tipTeam1;
    private int tipTeam2;
    private Game game;
    private int points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
