package ch.ergon.lernende.wmtippspiel.backend.game;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {

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
    void testGameDataResponse() {
        ResponseEntity<GamesTO[]> games = restTemplate.exchange(TestSetup.createBaseUrl(port) + "game?phase=group", HttpMethod.GET, entity, GamesTO[].class);
        List<GamesTO> gameData = List.of(Objects.requireNonNull(games.getBody()));

        assertTrue(gameData.size() >= 1);
    }
}