package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamToGroupControllerTest extends TestSetup {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void getTeamToGroups() {
        ResponseEntity<TeamToGroupTO[]> teamToGroupData = restTemplate.exchange(createBaseUrl(port) + "teamToGroup", HttpMethod.GET, entity, TeamToGroupTO[].class);
        List<TeamToGroupTO> teamToGroups = List.of(Objects.requireNonNull(teamToGroupData.getBody()));

        assertTrue(teamToGroups.size() >= 1);

        TeamToGroupTO teamToGroup = teamToGroups.get(0);
        assertEquals(16, teamToGroup.getTeamId());
        assertEquals("Katar", teamToGroup.getTeamCountry());
        assertEquals(1, teamToGroup.getGroupId());
        assertEquals("A", teamToGroup.getGroupName());
    }
}