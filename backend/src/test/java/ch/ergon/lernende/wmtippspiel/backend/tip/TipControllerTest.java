package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.util.TestSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TipControllerTest extends TestSetup {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void getTips() {
        ResponseEntity<TipTO[]> tipData = restTemplate.exchange(createBaseUrl(port) + "tip", HttpMethod.GET, entity, TipTO[].class);
        List<TipTO> tips = List.of(Objects.requireNonNull(tipData.getBody()));

        assertTrue(tips.size() >= 1);

        TipTO tip = tips.get(0);
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
    void addAndDeleteTip() {
        TipTO newTip = new TipTO();
        newTip.setUserId(1);
        newTip.setTipTeam1(50);
        newTip.setTipTeam2(60);
        newTip.setGameId(48);

        HttpEntity<TipTO> postEntity = new HttpEntity<>(newTip, httpHeaders);
        restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.POST, postEntity, HttpStatus.class);

        ResponseEntity<TipTO[]> tipData = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.GET, entity, TipTO[].class);
        List<TipTO> tips = List.of(Objects.requireNonNull(tipData.getBody()));
        assertTrue(tips.size() >= 1);

        Optional<TipTO> tip = tips.stream().filter(tipTO -> tipTO.getGameId() == newTip.getGameId() && tipTO.getUserId() == newTip.getUserId()).findFirst();
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

        ResponseEntity<HttpStatus> response = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.DELETE, deleteEntity, HttpStatus.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Dieser Test funktioniert erst, wenn der PATCH request zu einem PUT request abgeändert wurde.
//    @Test
//    void updateTip() {
//        TipTO updatedTip = new TipTO();
//        updatedTip.setUserId(1);
//        updatedTip.setTipTeam1(1);
//        updatedTip.setTipTeam2(1);
//        updatedTip.setGameId(1);
//
//        HttpEntity<TipTO> patchEntity = new HttpEntity<>(updatedTip, httpHeaders);
//        restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.PUT, patchEntity, HttpStatus.class);
//
//        ResponseEntity<TipTO[]> tipData = restTemplate.exchange(TestSetup.createBaseUrl(port) + "tip", HttpMethod.GET, entity, TipTO[].class);
//        List<TipTO> tips = List.of(Objects.requireNonNull(tipData.getBody()));
//        assertTrue(tips.size() >= 1);
//
//        Optional<TipTO> tip = tips.stream().filter(tipTO -> tipTO.getGameId() == updatedTip.getGameId() && tipTO.getUserId() == updatedTip.getUserId()).findFirst();
//        assertTrue(tip.isPresent());
//
//        TipTO tipTO = tip.get();
//        assertEquals(1, tipTO.getTipTeam1());
//        assertEquals(1, tipTO.getTipTeam2());
//        assertEquals(1, tipTO.getUserId());
//        assertEquals("Joel", tipTO.getFirstName());
//        assertEquals("Vontobel", tipTO.getLastName());
//        assertEquals("joel.vontobel@ergon.ch", tipTO.getEmail());
//        assertEquals(1, tipTO.getGameId());
//        assertEquals(LocalDateTime.of(2022, 11, 20, 17, 00), tipTO.getGameTime());
//        assertEquals("Katar", tipTO.getGameLocation());
//        assertEquals(16, tipTO.getTeamId1());
//        assertEquals(8, tipTO.getTeamId2());
//        assertEquals("Katar", tipTO.getTeamCountry1());
//        assertEquals("Ecuador", tipTO.getTeamCountry2());
//    }
}
