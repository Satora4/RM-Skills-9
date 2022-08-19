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
            CalculateObject calculateObject = new CalculateObject(tip.getTipTeam1(), tip.getTipTeam2(), gamesToCalculate.get(tip.getGame().getId()).getPointsTeam1(), gamesToCalculate.get(tip.getGame().getId()).getPointsTeam2());
            int points = ruleService.calculate(calculateObject);
            tip.setPoints(points);
            tipRepository.updateTip(tip);
        }
    }
}
