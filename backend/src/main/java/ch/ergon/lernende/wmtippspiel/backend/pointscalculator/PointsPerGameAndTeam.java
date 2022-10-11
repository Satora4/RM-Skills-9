package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import java.util.Objects;

/**
 * Holds the points for both teams of a game.
 */
public final class PointsPerGameAndTeam {
    private final int pointsTeam1;
    private final int pointsTeam2;

    private PointsPerGameAndTeam(int pointsTeam1, int pointsTeam2) {
        this.pointsTeam1 = pointsTeam1;
        this.pointsTeam2 = pointsTeam2;
    }

    public static PointsPerGameAndTeam winTeam1() {
        return new PointsPerGameAndTeam(3, 0);
    }

    public static PointsPerGameAndTeam winTeam2() {
        return new PointsPerGameAndTeam(0, 3);
    }

    public static PointsPerGameAndTeam draw() {
        return new PointsPerGameAndTeam(1, 1);
    }

    public int pointsTeam1() {
        return pointsTeam1;
    }

    public int pointsTeam2() {
        return pointsTeam2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PointsPerGameAndTeam) obj;
        return this.pointsTeam1 == that.pointsTeam1 &&
                this.pointsTeam2 == that.pointsTeam2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointsTeam1, pointsTeam2);
    }

    @Override
    public String toString() {
        return "PointsPerGameAndTeam[" +
                "pointsTeam1=" + pointsTeam1 + ", " +
                "pointsTeam2=" + pointsTeam2 + ']';
    }
}
