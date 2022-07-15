package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.TEAM;

@Repository
public class TeamRepository {

    private final DSLContext dslContext;

    @Autowired
    public TeamRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Team> getAllTeams() {
        return dslContext.selectFrom(TEAM).fetch(this::convert);
    }

    private Team convert(TeamRecord teamRecord) {
        Team team = new Team();
        team.setId(teamRecord.getTeamId());
        team.setCountry(teamRecord.getCountry());
        team.setPoints(teamRecord.getPoints());
        return team;
    }
}
