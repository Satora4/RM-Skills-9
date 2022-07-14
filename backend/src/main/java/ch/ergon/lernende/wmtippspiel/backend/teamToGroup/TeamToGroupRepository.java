package ch.ergon.lernende.wmtippspiel.backend.teamToGroup;

import ch.ergon.lernende.wmtippspiel.backend.group.Group;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.GroupTable;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.TEAM_TO_GROUP;
import static ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable.TEAM;

@Repository
public class TeamToGroupRepository {

    private static final TeamTable TEAM_ALIAS = TEAM.as("team");
    private static final GroupTable GROUP_ALIAS = GroupTable.GROUP.as("group");

    private final DSLContext dslContext;

    @Autowired
    public TeamToGroupRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<TeamToGroup> getAllTeamToGroup() {
        return dslContext
                .select(TEAM_ALIAS.TEAM_ID,
                        TEAM_ALIAS.COUNTRY,
                        TEAM_ALIAS.POINTS,
                        GROUP_ALIAS.GROUP_ID,
                        GROUP_ALIAS.NAME)
                .from(TEAM_TO_GROUP)
                .join(TEAM_ALIAS).on(TEAM_ALIAS.TEAM_ID.eq(TEAM_TO_GROUP.TEAM_ID))
                .join(GROUP_ALIAS).on(GROUP_ALIAS.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .fetch(this::convert);
    }

    private TeamToGroup convert(Record record) {
        TeamToGroup teamToGroup = new TeamToGroup();

        Team team = new Team();
        team.setId(record.get(TEAM_ALIAS.TEAM_ID));
        team.setCountry(record.get(TEAM_ALIAS.COUNTRY));
        team.setPoints(record.get(TEAM_ALIAS.POINTS));
        teamToGroup.setTeam(team);

        Group group = new Group();
        group.setId(record.get(GROUP_ALIAS.GROUP_ID));
        group.setName(record.get(GROUP_ALIAS.NAME));
        teamToGroup.setGroup(group);

        return teamToGroup;
    }
}
