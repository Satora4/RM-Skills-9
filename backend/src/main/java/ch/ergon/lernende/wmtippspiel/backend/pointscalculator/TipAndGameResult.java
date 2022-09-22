package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import static java.lang.String.format;

public class TipAndGameResult {

    private final int tipTeam1;
    private final int tipTeam2;
    private final int pointsTeam1;
    private final int pointsTeam2;

    public TipAndGameResult(int tipTeam1, int tipTeam2, int pointsTeam1, int pointsTeam2) {
        if (tipTeam1 < 0 || tipTeam2 < 0) {
            throw new IllegalArgumentException(format("invalid tip value: %d/%d", tipTeam1, tipTeam2));
        } else {
            this.tipTeam1 = tipTeam1;
            this.tipTeam2 = tipTeam2;
        }

        if (pointsTeam1 < 0 || pointsTeam2 < 0) {
            throw new IllegalArgumentException(format("invalid point value: %d/%d", pointsTeam1, pointsTeam2));
        } else {
            this.pointsTeam1 = pointsTeam1;
            this.pointsTeam2 = pointsTeam2;
        }

    }

    public int getPointsTeam2() {
        return pointsTeam2;
    }

    public int getPointsTeam1() {
        return pointsTeam1;
    }

    public int getTipTeam2() {
        return tipTeam2;
    }

    public int getTipTeam1() {
        return tipTeam1;
    }
}
