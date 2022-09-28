package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<?> getAllGames(@RequestParam(required = false, name = "phase") String phase, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream().map(Object::toString).toList();
        System.out.printf("Hello, %s / %s%n", user, roles);
        if (phase == null) {
            return convert(gameRepository.getAllGames());
        } else if (phase.equals(KO_PHASE)) {
            return convertToGamesWithPhase(gameRepository.getGamesForKoPhase());
        } else if (phase.equals(GROUP_PHASE)) {
            return convertToGamesWithGroups(gameRepository.getGamesForGroups());
        } else if (phase.equals(GROUP_PHASE_ORDER_DATE)) {
            return convertToGamesWithDate(gameRepository.getGamesInGroupPhaseWithOutGroupName());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phase: " + phase);
        }
    }

    @GetMapping("phase")
    public List<GamesWithGroupTO> getGamesForGroups(@RequestParam(name = "phase") String phase) {
        if (Objects.equals(phase, GROUP_PHASE)) {
            return convertToGamesWithGroups(gameRepository.getGamesForGroups());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phase: " + phase);
        }
    }

    private GamesWithGroupTO convert(GamesWithGroup gamesWithGroup) {
        GamesWithGroupTO gamesWithGroupTO = new GamesWithGroupTO();

        gamesWithGroupTO.setGroupName(gamesWithGroup.getGroupName());
        gamesWithGroupTO.setGames(gamesWithGroup.getGames());
        return gamesWithGroupTO;
    }

    private List<GamesWithGroupTO> convertToGamesWithGroups(Collection<GamesWithGroup> gamesWithGroups) {
        return gamesWithGroups.stream().map(this::convert).collect(Collectors.toList());
    }

    private List<GamesWithDateTO> convertToGamesWithDate(Collection<GamesWithDate> gamesWithDates) {
        return gamesWithDates.stream()
                .sorted(Comparator.comparing(GamesWithDate::getGroupDate))
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private List<GamesWithKoRoundsTO> convertToGamesWithPhase(Collection<GamesWithKoRounds> gamesWithKoRounds) {
        return gamesWithKoRounds.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private GamesWithDateTO convert(GamesWithDate gamesWithDate) {
        GamesWithDateTO gamesWithGroupTO = new GamesWithDateTO();

        gamesWithGroupTO.setGroupDate(gamesWithDate.getGroupDate());
        gamesWithGroupTO.setGames(gamesWithDate.getGames().stream().sorted(Comparator.comparing(Game::getGameTime)).toList());
        return gamesWithGroupTO;
    }

    private GamesWithKoRoundsTO convert(GamesWithKoRounds gamesWithKoRounds) {
        GamesWithKoRoundsTO gamesWithKoRoundsTO = new GamesWithKoRoundsTO();

        gamesWithKoRoundsTO.setPhase(gamesWithKoRounds.getPhaseOfGames());
        gamesWithKoRoundsTO.setGames(gamesWithKoRounds.getGames().stream().sorted(Comparator.comparing(Game::getGameTime)).toList());
        return gamesWithKoRoundsTO;
    }

    private List<GameTO> convert(Collection<Game> games) {
        return games.stream().map(this::convert).collect(toList());
    }


    private GameTO convert(Game game) {
        GameTO gameTO = new GameTO();
        gameTO.setId(game.getId());
        gameTO.setGameTime(game.getGameTime());
        gameTO.setGameLocation(game.getGameLocation());
        gameTO.setPointsTeam1(game.getPointsTeam1());
        gameTO.setPointsTeam2(game.getPointsTeam2());
        gameTO.setTeamCountry1(game.getTeam1().getCountry());
        gameTO.setTeamCountry2(game.getTeam2().getCountry());
        gameTO.setPhase(game.getPhase());
        return gameTO;
    }
}
