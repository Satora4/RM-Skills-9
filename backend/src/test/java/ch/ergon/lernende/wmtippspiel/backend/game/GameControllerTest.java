package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.util.CreateBaseUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    @Test
    public void testGameDataResponse() throws JSONException {
        String gamesJson = restTemplate.getForObject(CreateBaseUrl.createBaseUrl(port) + "game", String.class);
        JSONArray games = new JSONArray(gamesJson);
        JSONObject game = games.getJSONObject(0);

        assertEquals(1, game.getInt("id"));
        assertEquals("2022-11-10T20:00:00", game.getString("gameTime"));
        assertEquals("Katar", game.getString("gameLocation"));
        assertEquals(2, game.getInt("pointsTeam1"));
        assertEquals(3, game.getInt("pointsTeam2"));
        assertEquals("Argentinian", game.getString("teamCountry1"));
        assertEquals("Switzerland", game.getString("teamCountry2"));
    }
}