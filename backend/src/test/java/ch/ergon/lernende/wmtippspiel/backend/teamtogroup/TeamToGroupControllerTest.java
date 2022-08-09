package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

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
class TeamToGroupControllerTest {

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
    void testIfTeamToGroupLoads() throws JSONException {
        String teamToGroupJson = this.restTemplate.getForObject(baseUrl + "teamToGroup", String.class);
        JSONArray teamToGroups = new JSONArray(teamToGroupJson);
        JSONObject teamToGroup = teamToGroups.getJSONObject(0);

        assertEquals(9, teamToGroup.getInt("teamId"));
        assertEquals("Argentinian", teamToGroup.getString("teamCountry"));
        assertEquals(9, teamToGroup.getInt("teamPoints"));
        assertEquals(1, teamToGroup.getInt("groupId"));
        assertEquals("A", teamToGroup.getString("groupName"));

        System.out.println(teamToGroup);
    }
}