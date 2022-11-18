package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
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
    private static final int GAME_RESULT_VALUE_2 = 2;
    private static final int GAME_RESULT_VALUE_3 = 3;
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

    @Test
    void testWrongTipCalculatesOnePoint() {
        Game game = new Game();
        game.setPhase(Phase.GROUP_PHASE);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_1, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(1, points);
    }

    @Test
    void testWrongTipCalculatesTwoPointsInKOPhase() {
        Game game = new Game();
        game.setPhase(Phase.LITTLE_FINAL);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_1, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(2, points);
    }

    @Test
    void testCorrectTipCalculatesEightPoints() {
        Game game = new Game();
        game.setPhase(Phase.GROUP_PHASE);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_2, GAME_RESULT_VALUE_3, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(8, points);
    }

    @Test
    void testCorrectTipCalculatesSixTeenPointsInKoPhase() {
        Game game = new Game();
        game.setPhase(Phase.LITTLE_FINAL);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_2, GAME_RESULT_VALUE_3, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(16, points);
    }

    @Test
    void testTipHasCorrectGoalDeviationCalculatesFivePoints() {
        Game game = new Game();
        game.setPhase(Phase.GROUP_PHASE);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_3, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(5, points);
    }

    @Test
    void testTipHasCorrectGoalDeviationCalculatesTenPointsInKoPhase() {
        Game game = new Game();
        game.setPhase(Phase.ROUND_OF_16);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_3, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(10, points);
    }

    @Test
    void testNotCorrectTieCalculatesThreePoints() {
        Game game = new Game();
        game.setPhase(Phase.GROUP_PHASE);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_2, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(3, points);
    }

    @Test
    void testNotCorrectTieCalculatesSixPointsInKoPhase() {
        Game game = new Game();
        game.setPhase(Phase.SEMI_FINAL);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_2, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(6, points);
    }

    @Test
    void testCorrectWinnerCalculatesThreePoints() {
        Game game = new Game();
        game.setPhase(Phase.GROUP_PHASE);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_1, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(3, points);
    }

    @Test
    void testCorrectWinnerCalculatesSixPointsInKoPhase() {
        Game game = new Game();
        game.setPhase(Phase.QUARTER_FINAL);
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_1, GAME_RESULT_VALUE_4, game);
        int points = ruleService.calculateTipScore(tipAndGameResult);
        assertEquals(6, points);
    }
}