package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

public class CalculateObject {
    private final int tipTeam1;
    private final int tipTeam2;
    private final int pointsTeam1;
    private final int pointsTeam2;


    public CalculateObject(int tipTeam1, int tipTeam2, int pointsTeam1, int pointsTeam2){
        this.tipTeam1 = tipTeam1;
        this.tipTeam2 = tipTeam2;
        this.pointsTeam1 = pointsTeam1;
        this.pointsTeam2 = pointsTeam2;
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
