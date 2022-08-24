package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RuleServiceTest {

    private RuleService ruleService;

    @BeforeEach
    void initializeRuleService() {
        ruleService = new RuleService();
    }

    @Test
    void testTipFalse() {
        int points = ruleService.calculate(new TipAndGameResult(2, 3, 4, 1));
        boolean condition = points == 0;
        assertTrue(condition);
    }

    @Test
    void testTipTrue() {
        int points = ruleService.calculate(new TipAndGameResult(2, 3, 2, 3));
        boolean condition = points == 3;
        assertTrue(condition);
    }

    @Test
    void testTipHasCorrectGoalDeviation() {
        int points = ruleService.calculate(new TipAndGameResult(2, 3, 3, 4));
        boolean condition = points == 2;
        assertTrue(condition);
    }

    @Test
    void testNotCorrectTie() {
        int points = ruleService.calculate(new TipAndGameResult(2, 2, 3, 3));
        boolean condition = points == 1;
        assertTrue(condition);
    }

    @Test
    void testCorrectWinner() {
        int points = ruleService.calculate(new TipAndGameResult(2, 3, 1, 3));
        boolean condition = points == 1;
        assertTrue(condition);
    }

    @Test
    void testReturnValue() {
        int points = ruleService.calculate(new TipAndGameResult(getRandomNumber(), getRandomNumber(), getRandomNumber(), getRandomNumber()));
        boolean condition = points <= 3 && points >= 0;
        assertTrue(condition);
    }

    private static int getRandomNumber() {
        int min = 0;
        int max = 15;
        Random random = new Random();
        return random.nextInt(max + min) + min;
    }
}