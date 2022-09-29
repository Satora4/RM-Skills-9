package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.IamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
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

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<GameTO> getAllGames(@RequestParam(required = false, name = "phase") String phase, Authentication authentication) {
        IamUser user = (IamUser) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream().map(Object::toString).toList();
        System.out.printf("Hello, %s / %s%n", user, roles);
        if (phase == null) {
            return convert(gameRepository.getAllGames());
        } else if (phase.equals(KO_PHASE)) {
            return convert(gameRepository.getGamesForKoPhase());
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
        gameTO.setFlagTeam1(game.getTeam1().getFlag());
        gameTO.setFlagTeam2(game.getTeam2().getFlag());
        gameTO.setPhase(game.getPhase());
        return gameTO;
    }
}
