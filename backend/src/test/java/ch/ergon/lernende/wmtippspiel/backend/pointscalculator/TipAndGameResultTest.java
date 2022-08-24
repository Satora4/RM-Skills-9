package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipAndGameResultTest {

    @Test
    void testValidTipAndGameResult() {
        var tipAndGameResult = new TipAndGameResult(1,2,3,4);
        assertEquals(1, tipAndGameResult.getTipTeam1());
        assertEquals(2, tipAndGameResult.getTipTeam2());
        assertEquals(3, tipAndGameResult.getPointsTeam1());
        assertEquals(4, tipAndGameResult.getPointsTeam2());
    }

    @Test
    void testInvalidTipTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(-1,2,3,4));
    }

    @Test
    void testInvalidTipTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,-2,3,4));
    }

    @Test
    void testInvalidPointTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,2,-3,4));
    }

    @Test
    void testInvalidPointTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new TipAndGameResult(1,2,3,-4));
    }
}