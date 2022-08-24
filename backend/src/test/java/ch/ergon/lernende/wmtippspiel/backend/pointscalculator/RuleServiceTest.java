package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        ruleService = new RuleService();
    }

    @Test
    void testWrongTipCalculatesZeroPoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_1);
        int points = ruleService.calculate(tipAndGameResult);
        assertEquals(0, points);
    }

    @Test
    void testCorrectTipCalculatesThreePoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_2, GAME_RESULT_VALUE_3);
        int points = ruleService.calculate(tipAndGameResult);
        assertEquals(3, points);
    }

    @Test
    void testTipHasCorrectGoalDeviationCalculatesTwoPoints() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_3, GAME_RESULT_VALUE_4);
        int points = ruleService.calculate(tipAndGameResult);
        assertEquals(2, points);
    }

    @Test
    void testNotCorrectTieCalculatesOnePoint() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_2, GAME_RESULT_VALUE_4, GAME_RESULT_VALUE_4);
        int points = ruleService.calculate(tipAndGameResult);
        assertEquals(1, points);
    }

    @Test
    void testCorrectWinnerCalculatesOnePoint() {
        var tipAndGameResult = new TipAndGameResult(USER_TIP_VALUE_2, USER_TIP_VALUE_3, GAME_RESULT_VALUE_1, GAME_RESULT_VALUE_4);
        int points = ruleService.calculate(tipAndGameResult);
        assertEquals(1, points);
    }
}