package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static ch.ergon.lernende.wmtippspiel.backend.pointscalculator.RuleService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleServiceTest {

    private static final int USER_TIP_VALUE_2 = 2;
    private static final int USER_TIP_VALUE_3 = 3;
    private static final int GAME_RESULT_VALUE_4 = 4;
    private static final int GAME_RESULT_VALUE_1 = 1;
    public static final int GAME_RESULT_VALUE_2 = 2;
    public static final int GAME_RESULT_VALUE_3 = 3;
    private RuleService ruleService;

    @BeforeEach
    void initializeRuleService() {
        GameRepository gameRepositoryMock = Mockito.mock(GameRepository.class);
        ruleService = new RuleService(gameRepositoryMock);
    }

    @Test
    void calculateGameScoreDraw() {
        Game game = new Game();
        game.setGoalsTeam2(1);
        game.setGoalsTeam1(1);
        var pointsPerGameAndTeam = ruleService.calculateGame(game);
        assertEquals(pointsPerGameAndTeam, draw());
    }

    @Test
    void calculateGameScoreWinTeam1() {
        Game game = new Game();
        game.setGoalsTeam2(1);
        game.setGoalsTeam1(2);
        var pointsPerGameAndTeam = ruleService.calculateGame(game);
        assertEquals(pointsPerGameAndTeam, winTeam1());
    }

    @Test
    void calculateGameScoreWinTeam2() {
        Game game = new Game();
        game.setGoalsTeam2(3);
        game.setGoalsTeam1(1);
        var pointsPerGameAndTeam = ruleService.calculateGame(game);
        assertEquals(pointsPerGameAndTeam, winTeam2());
    }

    //2 other tests

    @Test
    void testWrongTipCalculatesZeroPoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_1);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(0, points);
    }

    @Test
    void testCorrectTipCalculatesThreePoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_2, GAME_RESULT_VALUE_3);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(3, points);
    }

    @Test
    void testTipHasCorrectGoalDeviationCalculatesTwoPoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_3, GAME_RESULT_VALUE_4);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(2, points);
    }

    @Test
    void testNotCorrectTieCalculatesOnePoint() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_2, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_4);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(1, points);
    }

    @Test
    void testCorrectWinnerCalculatesOnePoint() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_1, GAME_RESULT_VALUE_4);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(1, points);
    }
}