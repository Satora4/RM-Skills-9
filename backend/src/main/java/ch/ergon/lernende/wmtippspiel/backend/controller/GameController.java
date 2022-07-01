package ch.ergon.lernende.wmtippspiel.backend.controller;

import ch.ergon.lernende.wmtippspiel.backend.repository.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.to.GameTO;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.GameRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game")
    public List<GameTO> getAllGames() {
        return gameRepository.getAllGames().stream().map(this::convert).collect(Collectors.toList());
    }

    private GameTO convert(GameRecord gameRecord) {
        GameTO gameTO = new GameTO();
        gameTO.setId(gameRecord.getGameId());
        gameTO.setPointsTeam1(gameRecord.getPointsTeam1());
        gameTO.setPointsTeam2(gameRecord.getPointsTeam2());
        return gameTO;
    }
}
