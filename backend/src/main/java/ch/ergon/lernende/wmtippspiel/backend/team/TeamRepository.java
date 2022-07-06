package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.Tables;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamRepository {

    private final DSLContext dslContext;

    @Autowired
    public TeamRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<TeamRecord> getAllTeams() {
        return dslContext.selectFrom(Tables.TEAM).fetch();
    }
}
