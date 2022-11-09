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
        ResponseEntity<GroupTO[]> groupData = restTemplate.exchange(createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
        List<GroupTO> groups = List.of(Objects.requireNonNull(groupData.getBody()));

        assertTrue(groups.size() >= 1);

        groups.forEach(groupTO -> assertEquals(4, groupTO.getGroupMembers().size()));
    }

    @Test
    void groupsAreSorted() {
        ResponseEntity<GroupTO[]> groupData = restTemplate.exchange(createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
        List<GroupTO> groups = List.of(Objects.requireNonNull(groupData.getBody()));

        assertEquals(8, groups.size());

        assertEquals("A", groups.get(0).getName());
        assertEquals("B", groups.get(1).getName());
        assertEquals("C", groups.get(2).getName());
        assertEquals("D", groups.get(3).getName());
        assertEquals("E", groups.get(4).getName());
        assertEquals("F", groups.get(5).getName());
        assertEquals("G", groups.get(6).getName());
        assertEquals("H", groups.get(7).getName());
    }
}
