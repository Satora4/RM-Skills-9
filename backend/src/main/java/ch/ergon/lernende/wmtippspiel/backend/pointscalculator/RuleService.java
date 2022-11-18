package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    private final GameRepository gameRepository;

    public RuleService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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

    /**
     * calculates the score for each tip, on the basis of the ruleset
     *
     * @param tipAndGameResult tip and game result
     * @return int
     */
    public int calculateTipScore(TipAndGameResult tipAndGameResult) {
        int result;
        Game game = tipAndGameResult.getGame();
        if (isTipEqualGameResult(tipAndGameResult)) {
            result = 8;
        } else if (isGoalDifferenceCorrect(tipAndGameResult) && !isDraw(tipAndGameResult)) {
            result = 5;
        } else if (isGoalDifferenceCorrect(tipAndGameResult) && isDraw(tipAndGameResult)) {
            result = 3;
        } else if (isCorrectTendency(tipAndGameResult)) {
            result = 3;
        } else {
            result = 1;
        }

        if (isCurrentPhaseKoPhase(game)) {
            result *= 2;
        }
        return result;
    }

    private boolean isCurrentPhaseKoPhase(Game game) {
        return !game.getPhase().equals(Phase.GROUP_PHASE);
    }

    private static boolean isTipEqualGameResult(TipAndGameResult tipAndGameResult) {
        return tipAndGameResult.getTipTeam1() == tipAndGameResult.goalsTeam1() && tipAndGameResult.getTipTeam2() == tipAndGameResult.goalsTeam2();
    }

    private static boolean isGoalDifferenceCorrect(TipAndGameResult tipAndGameResult) {
        return tipAndGameResult.getTipTeam1() - tipAndGameResult.getTipTeam2() == tipAndGameResult.goalsTeam1() - tipAndGameResult.goalsTeam2();
    }

    private static boolean isDraw(TipAndGameResult tipAndGameResult) {
        return tipAndGameResult.getTipTeam1() - tipAndGameResult.getTipTeam2() == 0;
    }

    private static boolean isCorrectTendency(TipAndGameResult tipAndGameResult) {
        return tipAndGameResult.getTipTeam1() > tipAndGameResult.getTipTeam2() && tipAndGameResult.goalsTeam1() > tipAndGameResult.goalsTeam2() ||
                tipAndGameResult.getTipTeam1() < tipAndGameResult.getTipTeam2() && tipAndGameResult.goalsTeam1() < tipAndGameResult.goalsTeam2();
    }

    public PointsPerGameAndTeam calculateGame(Game game) {
        gameRepository.markAsCalculated(game);
        if (game.getGoalsTeam1().equals(game.getGoalsTeam2())) {
            return draw();
        } else if (game.getGoalsTeam1() > game.getGoalsTeam2()) {
            return winTeam1();
        } else {
            return winTeam2();
        }
    }
}
