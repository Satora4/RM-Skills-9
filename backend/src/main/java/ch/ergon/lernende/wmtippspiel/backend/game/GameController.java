package ch.ergon.lernende.wmtippspiel.backend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<GameTO> getAllGames(@RequestParam(required = false, name = "phase") String phase) {
        return switch (phase) {
            case KO_PHASE -> convertToGamesWithKoPhase(gameRepository.getGamesForKoPhase());
            case GROUP_PHASE -> convertToGamesWithGroups(gameRepository.getGamesForGroups());
            case GROUP_PHASE_ORDER_DATE ->
                    convertToGamesWithDate(gameRepository.getGamesInGroupPhaseWithOutGroupName());
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phase: " + phase);
        };
    }

    private List<GameTO> convertToGamesWithGroups(Collection<GamesWithGroup> gamesWithGroups) {
        return gamesWithGroups.stream().map(this::convert).collect(Collectors.toList());
    }

    private List<GameTO> convertToGamesWithDate(Collection<GamesWithDate> gamesWithDates) {
        return gamesWithDates.stream()
                .sorted(Comparator.comparing(GamesWithDate::getGroupDate))
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private List<GameTO> convertToGamesWithKoPhase(Collection<GamesWithKoRounds> gamesWithKoRounds) {
        return gamesWithKoRounds.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private GameTO convert(GamesWithGroup gamesWithGroup) {
        GameTO gamesWithGroupTO = new GameTO();

        gamesWithGroupTO.setGroupName(gamesWithGroup.getGroupName());
        gamesWithGroupTO.setGames(gamesWithGroup.getGames());
        return gamesWithGroupTO;
    }

    private GameTO convert(GamesWithDate gamesWithDate) {
        GameTO gamesWithGroupTO = new GameTO();

        gamesWithGroupTO.setGroupDate(gamesWithDate.getGroupDate());
        gamesWithGroupTO.setGames(gamesWithDate.getGames().stream().sorted(Comparator.comparing(Game::getGameTime)).toList());
        return gamesWithGroupTO;
    }

    private GameTO convert(GamesWithKoRounds gamesWithKoRounds) {
        GameTO gamesWithKoRoundsTO = new GameTO();

        gamesWithKoRoundsTO.setPhase(gamesWithKoRounds.getPhaseOfGames());
        gamesWithKoRoundsTO.setGames(gamesWithKoRounds.getGames().stream().sorted(Comparator.comparing(Game::getGameTime)).toList());
        return gamesWithKoRoundsTO;
    }
}
