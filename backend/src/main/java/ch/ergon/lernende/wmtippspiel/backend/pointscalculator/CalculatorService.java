package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.tip.Tip;
import ch.ergon.lernende.wmtippspiel.backend.tip.TipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    private final TipRepository tipRepository;
    private final GameRepository gameRepository;
    private final RuleService ruleService;

    public CalculatorService(TipRepository tipRepository, GameRepository gameRepository, RuleService ruleService) {
        this.tipRepository = tipRepository;
        this.gameRepository = gameRepository;
        this.ruleService = ruleService;
    }

    /**
     * get ready the data to calculate the score for the points of each tip
     */
    public void calculateScore() {

        List<Game> gamesToCalculate = gameRepository.getGamesWithPoints();

        List<Tip> tips = tipRepository.getAllTip();
        List<Tip> tipsToCalculate = new ArrayList<>();

        for (Tip tip : tips) {
            if (tip.getPoints() == null && gamesToCalculate.contains(tip.getGame())) {
                tipsToCalculate.add(tip);
            }
        }

        for (Tip tip : tipsToCalculate) {
            int tipTeam1 = tip.getTipTeam1();
            int tipTeam2 = tip.getTipTeam2();
            int gameId = tip.getGame().getId();
            Game currentGame = gamesToCalculate.stream().filter(game -> game.getId() == gameId).findFirst().orElseThrow();
            int pointsTeam1 = currentGame.getPointsTeam1();
            int pointsTeam2 = currentGame.getPointsTeam2();
            TipAndGameResult tipAndGameResult = new TipAndGameResult(tipTeam1, tipTeam2, pointsTeam1, pointsTeam2);
            int points = ruleService.calculate(tipAndGameResult);
            tip.setPoints(points);
            tipRepository.updateTip(tip);
        }
    }
}
