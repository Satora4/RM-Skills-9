package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
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
class GroupControllerTest extends TestSetup {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void testGroupDataResponse() {
        ResponseEntity<GroupTO[]> groups = restTemplate.exchange(createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
        List<GroupTO> groupData = List.of(Objects.requireNonNull(groups.getBody()));

        assertTrue(groupData.size() >= 1);

        GroupTO group = groupData.get(0);
        assertEquals(1, group.getId());
        assertEquals("A", group.getName());

        Team groupMember = group.getGroupMembers().get(0);
        assertEquals(4, group.getGroupMembers().size());
        assertEquals("Katar", groupMember.getCountry());
        assertEquals(16, groupMember.getId());
    }
}
