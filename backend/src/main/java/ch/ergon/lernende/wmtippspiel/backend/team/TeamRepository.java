package ch.ergon.lernende.wmtippspiel.backend.team;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.TEAM;

@Repository
public class TeamRepository {

    private final DSLContext dslContext;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamRepository(DSLContext dslContext, TeamMapper teamMapper) {
        this.dslContext = dslContext;
        this.teamMapper = teamMapper;
    }

    public List<Team> getAllTeams() {
        return dslContext.selectFrom(TEAM).fetch(teamMapper::map);
    }
}
