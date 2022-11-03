package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
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
class GroupControllerTest {

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
    void testGroupDataResponse() {
        ResponseEntity<GroupTO[]> groups = restTemplate.exchange(TestSetup.createBaseUrl(port) + "groups", HttpMethod.GET, entity, GroupTO[].class);
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
