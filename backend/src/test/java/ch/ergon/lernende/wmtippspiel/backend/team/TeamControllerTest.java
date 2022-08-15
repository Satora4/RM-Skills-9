package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
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
class TeamControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTeamDataResponse() throws JSONException {
        String teamsJson = restTemplate.getForObject(TestSetup.testSetup(port) + "team", String.class);
        JSONArray teams = new JSONArray(teamsJson);
        JSONObject team = teams.getJSONObject(0);

        assertEquals(1, team.getInt("teamId"));
        assertEquals("Switzerland", team.getString("country"));
        assertEquals(9, team.getInt("points"));
    }
}