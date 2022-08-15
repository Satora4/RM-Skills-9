package ch.ergon.lernende.wmtippspiel.backend.group;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroupControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGroupDataResponse() throws JSONException {
        String groupsJson = restTemplate.getForObject(TestSetup.testSetup(port) + "group", String.class);
        JSONArray groups = new JSONArray(groupsJson);
        JSONObject group = groups.getJSONObject(0);

        assertEquals(1, group.getInt("groupId"));
        assertEquals("A", group.getString("name"));
        assertNotNull(group.getJSONArray("groupMembers"));
        assertEquals("Argentinian", group.getJSONArray("groupMembers").getJSONObject(0).getString("country"));
        assertEquals(9, group.getJSONArray("groupMembers").getJSONObject(0).getInt("id"));
        assertEquals(9, group.getJSONArray("groupMembers").getJSONObject(0).getInt("points"));
    }
}
