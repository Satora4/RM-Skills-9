package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.tip.Tip;
import ch.ergon.lernende.wmtippspiel.backend.tip.TipRepository;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import ch.ergon.lernende.wmtippspiel.backend.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculatorService {

    private final TipRepository tipRepository;
    private final GameRepository gameRepository;
    private final RuleService ruleService;
    private final UserRepository userRepository;

    public CalculatorService(TipRepository tipRepository, GameRepository gameRepository, RuleService ruleService, UserRepository userRepository) {
        this.tipRepository = tipRepository;
        this.gameRepository = gameRepository;
        this.ruleService = ruleService;
        this.userRepository = userRepository;
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

        Map<User, List<Tip>> usersWithTips = groupTipsByUser(tipsToCalculate);

        for (var user : usersWithTips.keySet()) {
            int userPoints = user.getPoints();

            for (Tip tip : usersWithTips.get(user)) {
                int tipTeam1 = tip.getTipTeam1();
                int tipTeam2 = tip.getTipTeam2();
                int gameId = tip.getGame().getId();

                Game currentGame = gamesToCalculate.stream().filter(game -> game.getId() == gameId).findFirst().orElseThrow();
                int pointsTeam1 = currentGame.getPointsTeam1();
                int pointsTeam2 = currentGame.getPointsTeam2();

                TipAndGameResult tipAndGameResult = new TipAndGameResult(tipTeam1, tipTeam2, pointsTeam1, pointsTeam2);
                int points = ruleService.calculate(tipAndGameResult);
                tip.setPoints(points);

                userPoints += points;

                tipRepository.updateTip(tip);
            }

            user.setPoints(userPoints);
            userRepository.updateUser(user);
        }
    }

    private Map<User, List<Tip>> groupTipsByUser(List<Tip> tipsToCalculate) {
        Map<User, List<Tip>> usersWithTips = new HashMap<>();

        List<User> users = new ArrayList<>();
        for (Tip tip : tipsToCalculate) {
            if (!users.contains(tip.getUser())) {
                users.add(tip.getUser());
            }
        }
        for (User user : users) {
            List<Tip> tips = new ArrayList<>();
            for (Tip tip : tipsToCalculate) {
                if (tip.getUser().getId() == user.getId()) {
                    tips.add(tip);
                }
            }
            tips.forEach(tipsToCalculate::remove);
            usersWithTips.put(user, tips);
        }

        return usersWithTips;
    }
}
