package ch.ergon.lernende.wmtippspiel.backend.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeamController {
    private TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/team")
    public List<TeamTO> getAllTeams() {
        return teamRepository.getAllTeams().stream().map(this::convert).collect(Collectors.toList());
    }

    private TeamTO convert(Team team) {
        TeamTO teamTO = new TeamTO();
        teamTO.setId(team.getId());
        teamTO.setCountry(team.getCountry());
        teamTO.setPoints(team.getPoints());
        teamTO.setFlag(team.getFlag());
        return teamTO;
    }
}
