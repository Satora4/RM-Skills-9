package ch.ergon.lernende.wmtippspiel.backend.game;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest extends TestSetup {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void getGames() {
        ResponseEntity<GamesTO[]> games = restTemplate.exchange(createBaseUrl(port) + "game?phase=group", HttpMethod.GET, entity, GamesTO[].class);
        List<GamesTO> gameData = List.of(Objects.requireNonNull(games.getBody()));

        assertTrue(gameData.size() >= 1);
    }
}