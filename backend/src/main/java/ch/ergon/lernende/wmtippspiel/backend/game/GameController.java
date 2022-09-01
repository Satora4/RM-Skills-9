package ch.ergon.lernende.wmtippspiel.backend.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

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
    public List<GameTO> getAllGames(@RequestParam(required = false, name = "phase") String phase) {
        if (phase == null) {
            return convert(gameRepository.getAllGames());
        }
        switch (phase) {
            case GROUP_PHASE -> {
                return convert(gameRepository.getGamesForGroups());
            }
            case KO_PHASE -> {
                return convert(gameRepository.getGamesForKoPhase());
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phase: " + phase);
        }
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
