package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.user.User;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tip tip = (Tip) o;

        if (id != tip.id) return false;
        if (!Objects.equals(user, tip.user)) return false;
        if (!Objects.equals(tipTeam1, tip.tipTeam1)) return false;
        if (!Objects.equals(tipTeam2, tip.tipTeam2)) return false;
        if (!Objects.equals(game, tip.game)) return false;
        return Objects.equals(points, tip.points);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (tipTeam1 != null ? tipTeam1.hashCode() : 0);
        result = 31 * result + (tipTeam2 != null ? tipTeam2.hashCode() : 0);
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "id=" + id +
                ", user=" + user +
                ", tipTeam1=" + tipTeam1 +
                ", tipTeam2=" + tipTeam2 +
                ", game=" + game +
                ", points=" + points +
                '}';
    }
}
