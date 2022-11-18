package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipAndGameResultTest {

    @Test
    void testValidTipAndGameResult() {
        var tipAndGameResult = new TipAndGameResult(1,2,3,4, new Game());
        assertEquals(1, tipAndGameResult.getTipTeam1());
        assertEquals(2, tipAndGameResult.getTipTeam2());
        assertEquals(3, tipAndGameResult.goalsTeam1());
        assertEquals(4, tipAndGameResult.goalsTeam2());
    }

    @Test
    void testInvalidTipTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(-1,2,3,4, new Game()));
    }

    @Test
    void testInvalidTipTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,-2,3,4, new Game()));
    }

    @Test
    void testInvalidPointTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,2,-3,4, new Game()));
    }

    @Test
    void testInvalidPointTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,2,3,-4, new Game()));
    }
}