package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.team.TeamTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game")
    public List<GameTO> getAllGames() {
        return gameRepository.getAllGames().stream().map(this::convert).collect(toList());
    }

    @GetMapping("/gamesForGroupPhase")
    public List<GameTO> getGamesForGroups() {
        return gameRepository.getGamesForGroups().stream().map(this::convert).collect(toList());
    }

    @GetMapping("/gamesForKoPhase")
    public List<TeamTO> getGamesForKoPhase() {
        return gameRepository.getGamesForKoPhase().stream().map(this::convert).collect(toList());
    }

    private TeamTO convert(Team team) {
        var teamTO = new TeamTO();

        teamTO.setId(team.getId());
        teamTO.setCountry(team.getCountry());
        teamTO.setPoints(team.getPoints());
        teamTO.setPhase(team.getPhase());
        return teamTO;
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
        return gameTO;
    }
}
