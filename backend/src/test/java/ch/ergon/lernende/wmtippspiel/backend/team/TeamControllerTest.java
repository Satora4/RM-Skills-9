package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamControllerTest extends TestSetup {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void teamsHavePointsOf0() {
        ResponseEntity<TeamTO[]> teamData = restTemplate.exchange(createBaseUrl(port) + "team", HttpMethod.GET, entity, TeamTO[].class);
        List<TeamTO> teams = List.of(Objects.requireNonNull(teamData.getBody()));

        assertEquals(64, teams.size());

        teams.forEach(teamTO -> assertEquals(0, teamTO.getPoints()));
    }
}