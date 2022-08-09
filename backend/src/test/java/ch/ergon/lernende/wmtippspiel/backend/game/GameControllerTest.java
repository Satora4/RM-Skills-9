package ch.ergon.lernende.wmtippspiel.backend.game;

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
class GameControllerTest {

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
    public void gameLoads() throws JSONException {
        String gamesJson = this.restTemplate.getForObject(baseUrl + "game", String.class);
        JSONArray games = new JSONArray(gamesJson);
        JSONObject game = games.getJSONObject(0);

        assertEquals(1, game.getInt("id"));
        assertEquals("2022-11-10T20:00:00", game.getString("gameTime"));
        assertEquals("Katar", game.getString("gameLocation"));
        assertEquals(2, game.getInt("pointsTeam1"));
        assertEquals(3, game.getInt("pointsTeam2"));
        assertEquals("Argentinian", game.getString("teamCountry1"));
        assertEquals("Switzerland", game.getString("teamCountry2"));

        System.out.println(game);
    }
}