package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;


import org.springframework.stereotype.Service;

@Service
public class RuleService {

    /**
     * calculates the score for each tip, on the basis of the ruleset
     * @param calculateObject
     * @return int
     */
    public int calculate(CalculateObject calculateObject) {
        if (calculateObject.getTipTeam1() == calculateObject.getPointsTeam1() && calculateObject.getTipTeam2() == calculateObject.getPointsTeam2()) {
            return 3;
        } else if (calculateObject.getTipTeam1() - calculateObject.getPointsTeam1() == -calculateObject.getPointsTeam2()) {
            if (calculateObject.getTipTeam1() - calculateObject.getTipTeam2() == 0) {
                return 1;
            } else {
                return 2;
            }
        } else if (calculateObject.getTipTeam1() > calculateObject.getTipTeam2() && calculateObject.getPointsTeam1() > calculateObject.getPointsTeam2() || calculateObject.getTipTeam1() < calculateObject.getTipTeam2() && calculateObject.getPointsTeam1() < calculateObject.getPointsTeam2()) {
            return 1;
        } else {
            return 0;
        }
    }
}
