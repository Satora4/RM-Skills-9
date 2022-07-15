package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

import ch.ergon.lernende.wmtippspiel.backend.group.Group;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
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

        Team team = teamToGroup.getTeam();
        Group group = teamToGroup.getGroup();

        TeamToGroupTO teamToGroupTO = new TeamToGroupTO();

        teamToGroupTO.setGroupId(group.getId());
        teamToGroupTO.setGroupName(group.getName());

        teamToGroupTO.setTeamId(team.getId());
        teamToGroupTO.setTeamCountry(team.getCountry());
        teamToGroupTO.setTeamPoints(team.getPoints());
        return teamToGroupTO;
    }
}
