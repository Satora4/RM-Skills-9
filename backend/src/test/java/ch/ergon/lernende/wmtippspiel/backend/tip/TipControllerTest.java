package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TipControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTipDataResponse() throws JSONException {
        ResponseEntity<TipTO> tipsJson = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "tip", TipTO.class);
        JSONArray tips = new JSONArray(tipsJson);
        JSONObject tip = tips.getJSONObject(0);

        assertEquals(1, tip.getInt("id"));
        assertEquals(2, tip.getInt("tipTeam1"));
        assertEquals(2, tip.getInt("tipTeam2"));
        assertEquals(1, tip.getInt("userId"));
        assertEquals("Niculin", tip.getString("firstName"));
        assertEquals("Steiner", tip.getString("lastName"));
        assertEquals("steiner.niculin@mail.ch", tip.getString("email"));
        assertEquals(8, tip.getInt("gameId"));
        assertEquals("2022-11-13T22:00:00", tip.getString("gameTime"));
        assertEquals("Katar", tip.getString("gameLocation"));
        assertEquals(6, tip.getInt("teamId1"));
        assertEquals(4, tip.getInt("teamId2"));
        assertEquals(3, tip.getInt("pointsTeam1"));
        assertEquals(4, tip.getInt("pointsTeam2"));
        assertEquals("Germany", tip.getString("teamCountry1"));
        assertEquals("England", tip.getString("teamCountry2"));
    }

    @Test
    void testAddTip() throws JSONException {
        TipTO newTip = new TipTO();
        newTip.setUserId(2);
        newTip.setTipTeam1(50);
        newTip.setTipTeam2(60);
        newTip.setGameId(1);

        restTemplate.postForEntity(TestSetup.createBaseUrl(port) + "tip", newTip, TipTO.class);

        ResponseEntity<TipTO> tipsJson = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "tip", TipTO.class);
        JSONArray tips = new JSONArray(tipsJson);
        JSONObject tip = tips.getJSONObject(tips.length() - 1);

        assertEquals(5, tip.getInt("id"));
        assertEquals(50, tip.getInt("tipTeam1"));
        assertEquals(60, tip.getInt("tipTeam2"));
        assertEquals(2, tip.getInt("userId"));
        assertEquals("Joel", tip.getString("firstName"));
        assertEquals("Vontobel", tip.getString("lastName"));
        assertEquals("joel.vontobel@ergon.ch", tip.getString("email"));
        assertEquals(1, tip.getInt("gameId"));
        assertEquals("2022-11-10T20:00:00", tip.getString("gameTime"));
        assertEquals("Katar", tip.getString("gameLocation"));
        assertEquals(9, tip.getInt("teamId1"));
        assertEquals(1, tip.getInt("teamId2"));
        assertEquals(2, tip.getInt("pointsTeam1"));
        assertEquals(3, tip.getInt("pointsTeam2"));
        assertEquals("Argentinian", tip.getString("teamCountry1"));
        assertEquals("Switzerland", tip.getString("teamCountry2"));
    }
}
