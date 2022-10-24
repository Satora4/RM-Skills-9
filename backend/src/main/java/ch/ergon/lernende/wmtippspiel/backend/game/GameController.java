package ch.ergon.lernende.wmtippspiel.backend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ch.ergon.lernende.wmtippspiel.backend.game.GamesTO.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("game")
public class GameController {
    private final GameRepository gameRepository;
    private static final String GROUP_PHASE = "group";
    private static final String KO_PHASE = "ko";
    private static final String GROUP_PHASE_ORDER_DATE = "group_order_date";

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<GamesTO> getAllGames(@RequestParam(required = false, name = "phase") String phase) {
        return switch (phase) {
            case GROUP_PHASE -> convertToGroupTo(gameRepository.getGamesForGroups());
            case GROUP_PHASE_ORDER_DATE -> convertToDateTo(gameRepository.getGamesInGroupPhaseWithOutGroupName());
            case KO_PHASE -> convertToKoTo(gameRepository.getGamesForKoPhase(calculateDate()));
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phase: " + phase);
        };
    }

    private LocalDateTime calculateDate() {
        return LocalDateTime.now().plusDays(2);
    }

    private static List<GamesTO> convertToGroupTo(Collection<Games> gamesWithGroups) {
        return gamesWithGroups.stream()
                .map(gamesWithGroup -> gamesWithGroup(gamesWithGroup.getGames(), gamesWithGroup.getGroupName().orElseThrow()))
                .sorted(comparing(GamesTO::getGroupName))
                .collect(toList());
    }

    private static List<GamesTO> convertToDateTo(Collection<Games> gamesWithDates) {
        return gamesWithDates.stream()
                .map(gamesWithGroup -> gamesWithDate(gamesWithGroup.getGames(), gamesWithGroup.getDate().orElseThrow()))
                .sorted(comparing(GamesTO::getGroupDate))
                .collect(toList());
    }

    private static List<GamesTO> convertToKoTo(Collection<Games> gamesWithKoRounds) {
        return gamesWithKoRounds.stream()
                .map(gamesWithGroup -> gamesWithKoPhases(gamesWithGroup.getGames(), gamesWithGroup.getPhase().orElseThrow()))
                .collect(toList());
    }
}
