package ch.ergon.lernende.wmtippspiel.backend.user;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

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
    void testIfUserLoads() throws JSONException {
        String usersJson = this.restTemplate.getForObject(baseUrl + "user", String.class);
        JSONArray users = new JSONArray(usersJson);
        JSONObject user = users.getJSONObject(0);

        assertEquals(1, user.getInt("id"));
        assertEquals("Niculin", user.getString("firstName"));
        assertEquals("Steiner", user.getString("lastName"));
        assertEquals("steiner.niculin@mail.ch", user.getString("email"));
        assertEquals(12, user.getInt("points"));
        assertEquals(2, user.getInt("ranking"));
        assertTrue(user.getBoolean("administrator"));

        System.out.println(user);
    }
}
