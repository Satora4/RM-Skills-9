package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;
import static java.util.stream.Collectors.*;

@Repository
public class GroupRepository {

    private final DSLContext dslContext;

    @Autowired
    public GroupRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Group> getAllGroups() {

        Map<Group, List<Team>> result = dslContext.select(GROUP.GROUP_ID, GROUP.NAME, TEAM.TEAM_ID, TEAM.COUNTRY, TEAM.POINTS, TEAM.FLAG)
                .from(GROUP)
                .join(TEAM_TO_GROUP)
                .on(GROUP.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .join(TEAM)
                .on(TEAM.TEAM_ID.eq(TEAM_TO_GROUP.TEAM_ID))
                .collect(groupingBy(this::convertToGroup, mapping(this::convertToTeam, toList())));
        return convert(result);
    }

    private Team convertToTeam(Record record) {
        Team team = new Team();
        team.setId(record.get(TEAM.TEAM_ID));
        team.setCountry(record.get(TEAM.COUNTRY));
        team.setPoints(record.get(TEAM.POINTS));
        team.setCountryFlag(record.get(TEAM.FLAG));
        return team;
    }

    private Group convertToGroup(Record record) {
        Group group = new Group();

        group.setId(record.get(GROUP.GROUP_ID));
        group.setName(record.get(GROUP.NAME));
        return group;
    }

    private List<Group> convert(Map<Group, List<Team>> result) {
        List<Group> groups = new ArrayList<>();
        for (var record : result.entrySet()) {
            Group group = record.getKey();
            group.setGroupMembers(record.getValue());
            groups.add(group);
        }
        return groups;
    }
}
