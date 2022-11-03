package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamControllerTest {

    public static HttpHeaders httpHeaders = new HttpHeaders();
    public static HttpEntity<String> entity;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
        httpHeaders.set("X-Forwarded-User", "jvontobe");
        httpHeaders.set("X-Forwarded-Mail", "joel.vontobel@ergon.ch");

        entity = new HttpEntity<>(null, httpHeaders);
    }

    @Test
    void testTeamDataResponse() {
        ResponseEntity<TeamTO[]> teams = restTemplate.exchange(TestSetup.createBaseUrl(port) + "team", HttpMethod.GET, entity, TeamTO[].class);
        List<TeamTO> teamData = List.of(Objects.requireNonNull(teams.getBody()));

        assertTrue(teamData.size() >= 1);

        TeamTO team = teamData.get(0);
        assertEquals(1, team.getId());
        assertEquals("Argentinien", team.getCountry());
        assertEquals(0, team.getPoints());
    }
}