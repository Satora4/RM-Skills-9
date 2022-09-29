package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.TeamRecord;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team map(TeamRecord teamRecord) {
        Team team = new Team();
        team.setId(teamRecord.getTeamId());
        team.setCountry(teamRecord.getCountry());
        team.setPoints(teamRecord.getPoints());
        team.setFlag(teamRecord.getFlag());
        return team;
    }
}
