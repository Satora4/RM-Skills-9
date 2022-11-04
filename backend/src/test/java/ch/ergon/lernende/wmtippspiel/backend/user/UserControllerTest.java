package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernende.wmtippspiel.backend.tip.TipTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    public static HttpHeaders httpHeaders = new HttpHeaders();
    public static HttpEntity<TipTO> entity;

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
    void testUserDataResponse() {
        ResponseEntity<UserTO[]> users = restTemplate.exchange(TestSetup.createBaseUrl(port) + "users", HttpMethod.GET, entity, UserTO[].class);
        List<UserTO> userData = List.of(Objects.requireNonNull(users.getBody()));

        assertTrue(userData.size() >= 1);

        Optional<UserTO> user = userData.stream().filter(userTO -> userTO.getUserId() == 1).findFirst();
        assertTrue(user.isPresent());

        UserTO userTO = user.get();
        assertEquals(1, userTO.getUserId());
        assertEquals("Joel", userTO.getFirstName());
        assertEquals("Vontobel", userTO.getLastName());
        assertEquals("joel.vontobel@ergon.ch", userTO.getEmail());
        assertTrue(userTO.isAdministrator());
    }
}
