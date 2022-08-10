package ch.ergon.lernende.wmtippspiel.backend.tip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TipControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/";
    }

    @Test
    void testIfTipLoads() throws JSONException {
        String tipsJson = this.restTemplate.getForObject(baseUrl + "tip", String.class);
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

        System.out.println(tip);
    }

    @Test
    void testAddTip() throws JSONException {
        TipTO newTip = new TipTO();
        newTip.setUserId(2);
        newTip.setTipTeam1(50);
        newTip.setTipTeam2(60);
        newTip.setGameId(1);

        this.restTemplate.postForEntity(baseUrl + "tip", newTip, TipTO.class);

        String tipsJson = this.restTemplate.getForObject(baseUrl + "tip", String.class);
        JSONArray tips = new JSONArray(tipsJson);
        JSONObject tip = tips.getJSONObject(tips.length() - 1);

        System.out.println(tips);
    }
}
