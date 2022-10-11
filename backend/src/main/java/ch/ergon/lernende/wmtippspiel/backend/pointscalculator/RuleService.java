package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import org.springframework.stereotype.Service;

import static ch.ergon.lernende.wmtippspiel.backend.pointscalculator.PointsPerGameAndTeam.*;

@Service
public class RuleService {
    private final GameRepository gameRepository;

    public RuleService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * calculates the score for each tip, on the basis of the ruleset
     *
     * @param tipAndGameResult
     * @return int
     */
    public int calculateScore(TipAndGameResult tipAndGameResult) {
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

    public PointsPerGameAndTeam calculateGame(Game game) {
        gameRepository.markAsCalculated(game);
        if (game.getPointsTeam1().equals(game.getPointsTeam2())) {
            return draw();
        } else if (game.getPointsTeam1() > game.getPointsTeam2()) {
            return winTeam1();
        } else {
            return winTeam2();
        }
    }
}
