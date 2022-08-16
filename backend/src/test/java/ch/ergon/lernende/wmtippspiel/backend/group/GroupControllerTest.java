package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.json.JSONException;
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
class GroupControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGroupDataResponse() throws JSONException {
        ResponseEntity<GroupTO[]> groups = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "group", GroupTO[].class);
        List<GroupTO> groupData = List.of(Objects.requireNonNull(groups.getBody()));

        assertTrue(groupData.size() >= 1);

        GroupTO group = groupData.get(0);
        assertEquals(1, group.getId());
        assertEquals("A", group.getName());

        Team groupMember = group.getGroupMembers().get(0);
        assertEquals(1, group.getGroupMembers().size());
        assertEquals("Argentinian", groupMember.getCountry());
        assertEquals(9, groupMember.getId());
        assertEquals(9, groupMember.getPoints());
    }
}
