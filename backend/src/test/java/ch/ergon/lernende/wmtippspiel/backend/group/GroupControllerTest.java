package ch.ergon.lernende.wmtippspiel.backend.group;

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
class GroupControllerTest {

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
    void testIfGroupLoads() throws JSONException {
        String groupsJson = this.restTemplate.getForObject(baseUrl + "group", String.class);
        JSONArray groups = new JSONArray(groupsJson);
        JSONObject group = groups.getJSONObject(0);

        assertEquals(1, group.getInt("groupId"));
        assertEquals("A", group.getString("name"));
        assertEquals("[{\"country\":\"Argentinian\",\"id\":9,\"points\":9}]", group.getJSONArray("groupMembers").toString());

        System.out.println(group);
    }
}