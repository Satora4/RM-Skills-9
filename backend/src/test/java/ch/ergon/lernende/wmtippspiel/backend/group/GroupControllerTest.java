package ch.ergon.lernende.wmtippspiel.backend.group;

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
    void groupsHave4Teams() {
        ResponseEntity<GroupTO[]> groups = restTemplate.exchange(createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
        List<GroupTO> groupData = List.of(Objects.requireNonNull(groups.getBody()));

        assertTrue(groupData.size() >= 1);

        groupData.forEach(groupTO -> assertEquals(4, groupTO.getGroupMembers().size()));
    }

    @Test
    void groupsAreSorted() {
        ResponseEntity<GroupTO[]> groups = restTemplate.exchange(createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
        List<GroupTO> groupData = List.of(Objects.requireNonNull(groups.getBody()));

        assertEquals(8, groupData.size());

        assertEquals("A", groupData.get(0).getName());
        assertEquals("B", groupData.get(1).getName());
        assertEquals("C", groupData.get(2).getName());
        assertEquals("D", groupData.get(3).getName());
        assertEquals("E", groupData.get(4).getName());
        assertEquals("F", groupData.get(5).getName());
        assertEquals("G", groupData.get(6).getName());
        assertEquals("H", groupData.get(7).getName());
    }
}
