package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGameDataResponse() {
        ResponseEntity<GameTO[]> games = restTemplate.getForEntity(TestSetup.createBaseUrl(port) + "game", GameTO[].class);
        List<GameTO> gameData = List.of(Objects.requireNonNull(games.getBody()));

        assertTrue(gameData.size() >= 1);

        GameTO game = gameData.get(0);
        assertEquals(1, game.getId());
        assertEquals(LocalDateTime.of(2022, 11, 10, 20, 00), game.getGameTime());
        assertEquals("Katar", game.getGameLocation());
        assertEquals(2, game.getPointsTeam1());
        assertEquals(3, game.getPointsTeam2());
        assertEquals("Argentinian", game.getTeamCountry1());
        assertEquals("Switzerland", game.getTeamCountry2());
    }
}