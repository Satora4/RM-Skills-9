package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.team.TeamRepository;
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
    private final TeamRepository teamRepository;

    public CalculatorService(TipRepository tipRepository, GameRepository gameRepository, RuleService ruleService, UserRepository userRepository, TeamRepository teamRepository) {
        this.tipRepository = tipRepository;
        this.gameRepository = gameRepository;
        this.ruleService = ruleService;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }


    public void calculateGames() {
        List<Game> gamesToCalculate = gameRepository.getAllFinishedGamesWithOutTeamCalculation();
        for (Game game : gamesToCalculate) {
            PointsPerGameAndTeam pointsPerGameAndTeam = ruleService.calculateGame(game);

            Team team1 = getTeam(game.getTeam1().getId());
            Team team2 = getTeam(game.getTeam2().getId());

            teamRepository.updateTeam(game.getTeam1().getId(),
                    team1.getPoints() + pointsPerGameAndTeam.pointsTeam1());
            teamRepository.updateTeam(game.getTeam2().getId(),
                    team2.getPoints() + pointsPerGameAndTeam.pointsTeam2());
        }
    }

    // Methode getTeamById in TeamRepository
    public Team getTeam(int teamId) {
        List<Team> allTeams = teamRepository.getAllTeams();

        for (Team team : allTeams) {
            if (team.getId() == teamId) {
                return team;
            }
        }
        throw new IllegalArgumentException("Team isnt in list`!");
    }

    /**
     * get ready the data to calculate the score for the points of each tip
     */
    // "calculateUserScores"
    // Diese Funktion würde ich vermutlich etwas auseinander nehmen.
    //
    public void calculateScore() {
        // Könnte das "CALCULATED"-Flag nicht auch als Anzeiger gebraucht werden, dass Tipps für diese Spiel bereits
        // gewertet sind? Dann müssten hier nur die noch nicht gewerteten Spiele geholte werden.
        List<Game> gamesToCalculate = gameRepository.getAllFinishedGames();

        // Hier müssten nur nicht gewertete Tipps geholt werden.
        List<Tip> tips = tipRepository.getAllTip();
        List<Tip> tipsToCalculate = new ArrayList<>();

        for (Tip tip : tips) {
            if (tip.getPoints() == null && gamesToCalculate.contains(tip.getGame())) {
                tipsToCalculate.add(tip);
            }
        }

        // den ganzen Teil über diesem Kommentar in eine Funktion "getTipsToCalculate()" auslagern
        // IntelliJ-Tip: Bereich markieren, Rechtsklick, Refactor->Extract Method

        Map<User, List<Tip>> usersWithTips = groupTipsByUser(tipsToCalculate);
        for (var user : usersWithTips.keySet()) {
            int userPoints = user.getPoints();

            for (Tip tip : usersWithTips.get(user)) {
                int tipTeam1 = tip.getTipTeam1();
                int tipTeam2 = tip.getTipTeam2();
                int gameId = tip.getGame().getId();

                // Hier ist eine Performance-Optimierung möglich:
                // Momentan wird jedes mal über die ganze gamesToCalculate Liste iteriert, um das richtige Game zu finden
                // Wie könnte man das schneller machen?
                Game currentGame = gamesToCalculate.stream().filter(game -> game.getId() == gameId).findFirst().orElseThrow();
                int pointsTeam1 = currentGame.getPointsTeam1();
                int pointsTeam2 = currentGame.getPointsTeam2();

                TipAndGameResult tipAndGameResult = new TipAndGameResult(tipTeam1, tipTeam2, pointsTeam1, pointsTeam2);
                int points = ruleService.calculateTipScore(tipAndGameResult);
                tip.setPoints(points);

                tipRepository.updateTipPoints(tip);

                userPoints += points;

                // den Loop-Body würde ich in eine
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
