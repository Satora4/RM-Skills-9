package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

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
class TeamToGroupControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTeamToGroupDataResponse() throws JSONException {
        ResponseEntity<TeamToGroupTO> teamToGroupJson = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "teamToGroup", TeamToGroupTO.class);
        JSONArray teamToGroups = new JSONArray(teamToGroupJson);
        JSONObject teamToGroup = teamToGroups.getJSONObject(0);

        assertEquals(9, teamToGroup.getInt("teamId"));
        assertEquals("Argentinian", teamToGroup.getString("teamCountry"));
        assertEquals(9, teamToGroup.getInt("teamPoints"));
        assertEquals(1, teamToGroup.getInt("groupId"));
        assertEquals("A", teamToGroup.getString("groupName"));
    }
}