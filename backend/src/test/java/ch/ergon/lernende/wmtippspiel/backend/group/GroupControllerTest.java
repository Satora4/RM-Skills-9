package ch.ergon.lernende.wmtippspiel.backend.group;

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
class GroupControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGroupDataResponse() throws JSONException {
        String groupsJson = restTemplate.getForObject(CreateBaseUrl.createBaseUrl(port) + "group", String.class);
        JSONArray groups = new JSONArray(groupsJson);
        JSONObject group = groups.getJSONObject(0);

        assertEquals(1, group.getInt("groupId"));
        assertEquals("A", group.getString("name"));
        assertEquals("[{\"country\":\"Argentinian\",\"id\":9,\"points\":9}]", group.getJSONArray("groupMembers").toString());
    }
}