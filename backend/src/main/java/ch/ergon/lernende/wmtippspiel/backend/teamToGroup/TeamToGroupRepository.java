package ch.ergon.lernende.wmtippspiel.backend.teamToGroup;

import ch.ergon.lernende.wmtippspiel.backend.group.Group;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

@Repository
public class TeamToGroupRepository {

    private final DSLContext dslContext;

    @Autowired
    public TeamToGroupRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<TeamToGroup> getAllTeamToGroup() {
        return dslContext
                .select(TEAM.COUNTRY, GROUP.NAME)
                .from(TEAM_TO_GROUP)
                .join(TEAM).on(TEAM.TEAM_ID.eq(TEAM_TO_GROUP.TEAM_ID))
                .join(GROUP).on(GROUP.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .fetch(this::convert);
    }

    private TeamToGroup convert(Record record) {
        TeamToGroup teamToGroup = new TeamToGroup();

        Team team = new Team();
        team.setCountry(record.get(TEAM.COUNTRY));
        teamToGroup.setTeamName(team);

        Group group = new Group();
        group.setName(record.get(GROUP.NAME));
        teamToGroup.setGroupName(group);

        return teamToGroup;
    }
}
