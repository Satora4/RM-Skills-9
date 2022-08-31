package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import org.springframework.stereotype.Service;

@Service
public class RuleService {

    /**
     * calculates the score for each tip, on the basis of the ruleset
     * @param tipAndGameResult
     * @return int
     */
    public int calculate(TipAndGameResult tipAndGameResult) {
        if (tipAndGameResult.getTipTeam1() == tipAndGameResult.getPointsTeam1() && tipAndGameResult.getTipTeam2() == tipAndGameResult.getPointsTeam2()) {
            return 3;
        } else if (tipAndGameResult.getTipTeam1() - tipAndGameResult.getPointsTeam1() == tipAndGameResult.getTipTeam2() - tipAndGameResult.getPointsTeam2()) {
            if (tipAndGameResult.getTipTeam1() - tipAndGameResult.getTipTeam2() == 0) {
                return 1;
            } else {
                return 2;
            }
        } else if (tipAndGameResult.getTipTeam1() > tipAndGameResult.getTipTeam2() && tipAndGameResult.getPointsTeam1() > tipAndGameResult.getPointsTeam2() || tipAndGameResult.getTipTeam1() < tipAndGameResult.getTipTeam2() && tipAndGameResult.getPointsTeam1() < tipAndGameResult.getPointsTeam2()) {
            return 1;
        } else {
            return 0;
        }
    }
}
