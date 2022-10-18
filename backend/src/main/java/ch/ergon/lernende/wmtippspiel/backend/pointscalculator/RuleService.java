package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import org.springframework.stereotype.Service;

import static ch.ergon.lernende.wmtippspiel.backend.pointscalculator.PointsPerGameAndTeam.*;

@Service
// Wenn der GameScore nicht mehr hier berechnet wird kann man die Klasse eigentlich spezifischer bennenen z.B. "TipScoring"
public class RuleService {
    private final GameRepository gameRepository;

    public RuleService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    // diese Methoden könnte man auch in die Game Klasse auslagern
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

    // Ich würde glaub ich eher mit 4 Parametern arbeiten (tipTeam1, tipTeam2, goalsTeam1, goalsTeam2) als mit TipAndGameResult, oder mindestens den Parameternamen kürzer machen
    // Evtl. würde ich die if-Bedingungen noch in einzelne Methode mit sprechendem Namen packen. Z.B. isPerfect(), hasCorrectGoalDifference() etc...
    // Die langen if-Bedingungen machen den Code hier nämlich recht unleserlich.
    // Die Methode kann man ausserdem "static" machen.
    public int calculateTipScore(TipAndGameResult tipAndGameResult) {
        if (tipAndGameResult.getTipTeam1() == tipAndGameResult.getPointsTeam1() && tipAndGameResult.getTipTeam2() == tipAndGameResult.getPointsTeam2()) {
            return 3;
            // Hier wird geprüft ob die Tor-Differenz stimmt, oder? Da würde ich eher erwarten tip1 - tip2 == goals1 - goals2
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

    // Würde ich in die Game Klasse verschieben als "calculateGame(GameRepository)", dann muss diese Klasse auch nicht
    // mehr injizierbar sein (@Service).
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
