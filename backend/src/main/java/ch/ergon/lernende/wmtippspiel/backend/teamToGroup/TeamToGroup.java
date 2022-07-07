package ch.ergon.lernende.wmtippspiel.backend.teamToGroup;

import ch.ergon.lernende.wmtippspiel.backend.group.Group;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;

public class TeamToGroup {

    private Team teamName;
    private Group groupName;

    public Team getTeamName() {
        return teamName;
    }

    public void setTeamName(Team teamName) {
        this.teamName = teamName;
    }

    public Group getGroupName() {
        return groupName;
    }

    public void setGroupName(Group groupName) {
        this.groupName = groupName;
    }
}
