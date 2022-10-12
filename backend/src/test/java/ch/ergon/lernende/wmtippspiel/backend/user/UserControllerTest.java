package ch.ergon.lernende.wmtippspiel.backend.user;

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
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUserDataResponse() {
        ResponseEntity<UserTO[]> users = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "user", UserTO[].class);
        List<UserTO> userData = List.of(Objects.requireNonNull(users.getBody()));

        assertTrue(userData.size() >= 1);

        UserTO user = userData.get(0);
        assertEquals(1, user.getId());
        assertEquals("Niculin", user.getFirstName());
        assertEquals("Steiner", user.getLastName());
        assertEquals("steiner.niculin@mail.ch", user.getEmail());
        assertEquals(12, user.getPoints());
        assertTrue(user.isAdministrator());
    }
}
