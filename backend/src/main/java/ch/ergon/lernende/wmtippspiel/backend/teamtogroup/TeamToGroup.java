package ch.ergon.lernende.wmtippspiel.backend.teamtogroup;

import ch.ergon.lernende.wmtippspiel.backend.group.Group;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;

public class TeamToGroup {

    private Team team;
    private Group group;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
