package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TipControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTipDataResponse() {
        ResponseEntity<TipTO[]> tips = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "tip", TipTO[].class);
        List<TipTO> tipData = List.of(Objects.requireNonNull(tips.getBody()));

        assertTrue(tipData.size() >= 1);

        TipTO tip = tipData.get(0);
        assertEquals(1, tip.getId());
        assertEquals(2, tip.getTipTeam1());
        assertEquals(2, tip.getTipTeam2());
        assertEquals(1, tip.getUserId());
        assertEquals("Niculin", tip.getFirstName());
        assertEquals("Steiner", tip.getLastName());
        assertEquals("steiner.niculin@mail.ch", tip.getEmail());
        assertEquals(8, tip.getGameId());
        assertEquals(LocalDateTime.of(2022, 11, 13, 22, 00), tip.getGameTime());
        assertEquals("Katar", tip.getGameLocation());
        assertEquals(6, tip.getTeamId1());
        assertEquals(4, tip.getTeamId2());
        assertEquals(3, tip.getPointsTeam1());
        assertEquals(4, tip.getPointsTeam2());
        assertEquals("Germany", tip.getTeamCountry1());
        assertEquals("England", tip.getTeamCountry2());
    }

    @Test
    void testAddTip() {
        TipTO newTip = new TipTO();
        newTip.setUserId(2);
        newTip.setTipTeam1(50);
        newTip.setTipTeam2(60);
        newTip.setGameId(1);

        restTemplate.postForEntity(TestSetup.createBaseUrl(port) + "tip", newTip, TipTO.class);

        ResponseEntity<TipTO[]> tips = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "tip", TipTO[].class);
        List<TipTO> tipData = List.of(Objects.requireNonNull(tips.getBody()));

        assertTrue(tipData.size() >= 1);

        TipTO tip = tipData.get(tipData.size() - 1);
        assertEquals(5, tip.getId());
        assertEquals(50, tip.getTipTeam1());
        assertEquals(60, tip.getTipTeam2());
        assertEquals(2, tip.getUserId());
        assertEquals("Joel", tip.getFirstName());
        assertEquals("Vontobel", tip.getLastName());
        assertEquals("joel.vontobel@ergon.ch", tip.getEmail());
        assertEquals(1, tip.getGameId());
        assertEquals(LocalDateTime.of(2022, 11, 10, 20, 00), tip.getGameTime());
        assertEquals("Katar", tip.getGameLocation());
        assertEquals(9, tip.getTeamId1());
        assertEquals(1, tip.getTeamId2());
        assertEquals(2, tip.getPointsTeam1());
        assertEquals(3, tip.getPointsTeam2());
        assertEquals("Argentinian", tip.getTeamCountry1());
        assertEquals("Switzerland", tip.getTeamCountry2());
    }
}
