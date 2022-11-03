package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    private final GameRepository gameRepository;
    private Game game;

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
     * @param tipAndGameResult
     * @return int
     */
    public int calculateTipScore(TipAndGameResult tipAndGameResult) {
        game = tipAndGameResult.getGame();
        if (tipAndGameResult.getTipTeam1() == tipAndGameResult.getPointsTeam1() && tipAndGameResult.getTipTeam2() == tipAndGameResult.getPointsTeam2()) {
            return checkPhase(8);
        } else if (tipAndGameResult.getTipTeam1() - tipAndGameResult.getPointsTeam1() == tipAndGameResult.getTipTeam2() - tipAndGameResult.getPointsTeam2()) {
            if (tipAndGameResult.getTipTeam1() - tipAndGameResult.getTipTeam2() == 0) {
                return checkPhase(3);
            } else {
                return checkPhase(5);
            }
        } else if (tipAndGameResult.getTipTeam1() > tipAndGameResult.getTipTeam2() && tipAndGameResult.getPointsTeam1() > tipAndGameResult.getPointsTeam2() || tipAndGameResult.getTipTeam1() < tipAndGameResult.getTipTeam2() && tipAndGameResult.getPointsTeam1() < tipAndGameResult.getPointsTeam2()) {
            return checkPhase(3);
        } else {
            return checkPhase(1);
        }
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

    private int checkPhase(int point) {
        if (game.getPhase().equals(Phase.GROUP_PHASE)) {
            return point;
        } else {
            return point * 2;
        }
    }
}
