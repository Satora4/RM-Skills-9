package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TipControllerTest {

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
    void testTipDataResponse() {
        ResponseEntity<TipTO[]> tips = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.GET, entity, TipTO[].class);
        List<TipTO> tipData = List.of(Objects.requireNonNull(tips.getBody()));
        
        assertTrue(tipData.size() >= 1);

        TipTO tip = tipData.get(0);
        assertEquals(2, tip.getTipTeam1());
        assertEquals(2, tip.getTipTeam2());
        assertEquals(1, tip.getUserId());
        assertEquals("Joel", tip.getFirstName());
        assertEquals("Vontobel", tip.getLastName());
        assertEquals("joel.vontobel@ergon.ch", tip.getEmail());
        assertEquals(1, tip.getGameId());
        assertEquals(LocalDateTime.of(2022, 11, 20, 17, 00), tip.getGameTime());
        assertEquals("Katar", tip.getGameLocation());
        assertEquals(16, tip.getTeamId1());
        assertEquals(8, tip.getTeamId2());
        assertEquals("Katar", tip.getTeamCountry1());
        assertEquals("Ecuador", tip.getTeamCountry2());
    }

    @Test
    void testAddAndDeleteTip() {
        TipTO newTip = new TipTO();
        newTip.setUserId(1);
        newTip.setTipTeam1(50);
        newTip.setTipTeam2(60);
        newTip.setGameId(48);

        HttpEntity<TipTO> postEntity = new HttpEntity<>(newTip, httpHeaders);
        restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.POST, postEntity, String.class);

        ResponseEntity<TipTO[]> tips = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.GET, entity, TipTO[].class);
        List<TipTO> tipData = List.of(Objects.requireNonNull(tips.getBody()));
        assertTrue(tipData.size() >= 1);

        Optional<TipTO> tip = tipData.stream().filter(tipTO -> tipTO.getGameId() == newTip.getGameId() && tipTO.getUserId() == newTip.getUserId()).findFirst();
        assertTrue(tip.isPresent());

        TipTO tipTO = tip.get();
        assertEquals(50, tipTO.getTipTeam1());
        assertEquals(60, tipTO.getTipTeam2());
        assertEquals(1, tipTO.getUserId());
        assertEquals("Joel", tipTO.getFirstName());
        assertEquals("Vontobel", tipTO.getLastName());
        assertEquals("joel.vontobel@ergon.ch", tipTO.getEmail());
        assertEquals(48, tipTO.getGameId());
        assertEquals(LocalDateTime.of(2022, 12, 02, 20, 00), tipTO.getGameTime());
        assertEquals("Katar", tipTO.getGameLocation());
        assertEquals(14, tipTO.getTeamId1());
        assertEquals(4, tipTO.getTeamId2());
        assertEquals("Kamerun", tipTO.getTeamCountry1());
        assertEquals("Brasilien", tipTO.getTeamCountry2());

        HttpEntity<Integer> deleteEntity = new HttpEntity<>(tip.get().getId(), httpHeaders);

        ResponseEntity<HttpStatus> respons = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.DELETE, deleteEntity, HttpStatus.class);
        HttpStatus responseData = Objects.requireNonNull(respons.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
