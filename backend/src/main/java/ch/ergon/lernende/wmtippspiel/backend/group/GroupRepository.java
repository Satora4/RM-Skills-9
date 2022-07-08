package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

@Repository
public class GroupRepository {

    private final DSLContext dslContext;

    @Autowired
    public GroupRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Group> getAllGroups() {

        Map<Group, List<Team>> result = dslContext.select(GROUP.GROUP_ID, GROUP.NAME, TEAM.TEAM_ID, TEAM.COUNTRY, TEAM.POINTS)
                .from(GROUP)
                .join(TEAM_TO_GROUP)
                .on(GROUP.GROUP_ID.eq(TEAM_TO_GROUP.GROUP_ID))
                .join(TEAM)
                .on(TEAM.TEAM_ID.eq(TEAM_TO_GROUP.TEAM_ID))
                .groupBy(GROUP.NAME, TEAM.COUNTRY)
                .collect(Collectors.groupingBy(r -> r.into(Group.class), Collectors.mapping(r -> r.into(Team.class), Collectors.toList())));
        return convert(result);
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
