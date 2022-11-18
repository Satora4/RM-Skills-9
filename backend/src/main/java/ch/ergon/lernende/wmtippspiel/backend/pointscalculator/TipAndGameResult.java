package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;

import static java.lang.String.format;

public class TipAndGameResult {

    private final int tipTeam1;
    private final int tipTeam2;
    private final int goalsTeam1;
    private final int goalsTeam2;

    public Game getGame() {
        return game;
    }

    private final Game game;

    public TipAndGameResult(int tipTeam1, int tipTeam2, int goalsTeam1, int goalsTeam2, Game game) {
        this.game = game;
        if (tipTeam1 < 0 || tipTeam2 < 0) {
            throw new IllegalArgumentException(format("invalid tip value: %d/%d", tipTeam1, tipTeam2));
        } else {
            this.tipTeam1 = tipTeam1;
            this.tipTeam2 = tipTeam2;
        }

        if (goalsTeam1 < 0 || goalsTeam2 < 0) {
            throw new IllegalArgumentException(format("invalid point value: %d/%d", goalsTeam1, goalsTeam2));
        } else {
            this.goalsTeam1 = goalsTeam1;
            this.goalsTeam2 = goalsTeam2;
        }

    }

    public int goalsTeam2() {
        return goalsTeam2;
    }

    public int goalsTeam1() {
        return goalsTeam1;
    }

    public int getTipTeam2() {
        return tipTeam2;
    }

    public int getTipTeam1() {
        return tipTeam1;
    }
}
