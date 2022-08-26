package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamToGroupControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testTeamToGroupDataResponse() {
        ResponseEntity<TeamToGroupTO[]> teamToGroups = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "teamToGroup", TeamToGroupTO[].class);
        List<TeamToGroupTO> teamToGroupData = List.of(Objects.requireNonNull(teamToGroups.getBody()));

        assertTrue(teamToGroupData.size() >= 1);

        TeamToGroupTO teamToGroup = teamToGroupData.get(0);
        assertEquals(9, teamToGroup.getTeamId());
        assertEquals("Argentinian", teamToGroup.getTeamCountry());
        assertEquals(9, teamToGroup.getTeamPoints());
        assertEquals(1, teamToGroup.getGroupId());
        assertEquals("A", teamToGroup.getGroupName());
    }
}