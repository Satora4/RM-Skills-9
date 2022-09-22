package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.user.User;

public class Tip {
    private int id;
    private User user;
    private Integer tipTeam1;
    private Integer tipTeam2;
    private Game game;
    private Integer points;

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

    public Integer getTipTeam1() {
        return tipTeam1;
    }

    public void setTipTeam1(Integer tipTeam1) {
        this.tipTeam1 = tipTeam1;
    }

    public Integer getTipTeam2() {
        return tipTeam2;
    }

    public void setTipTeam2(Integer tipTeam2) {
        this.tipTeam2 = tipTeam2;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
