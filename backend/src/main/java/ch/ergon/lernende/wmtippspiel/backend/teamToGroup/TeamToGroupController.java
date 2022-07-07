package ch.ergon.lernende.wmtippspiel.backend.teamToGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeamToGroupController {
    private final TeamToGroupRepository teamToGroupRepository;

    @Autowired
    public TeamToGroupController(TeamToGroupRepository teamToGroupRepository) {
        this.teamToGroupRepository = teamToGroupRepository;
    }

    @GetMapping("/teamToGroup")
    public List<TeamToGroupTO> getAllTeamToGroup() {
        return teamToGroupRepository.getAllTeamToGroup().stream().map(this::convert).collect(Collectors.toList());
    }

    private TeamToGroupTO convert(TeamToGroup teamToGroup) {
        TeamToGroupTO teamToGroupTO = new TeamToGroupTO();
        teamToGroupTO.setTeamName(teamToGroup.getTeamName().getCountry());
        teamToGroupTO.setGroupName(teamToGroup.getGroupName().getName());
        return teamToGroupTO;
    }
}
