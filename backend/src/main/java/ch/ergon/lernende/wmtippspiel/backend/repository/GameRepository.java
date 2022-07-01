package ch.ergon.lernende.wmtippspiel.backend.repository;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.GameRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.GAME;

@Repository
public class GameRepository {
    private final DSLContext dslContext;

    @Autowired
    public GameRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<GameRecord> getAllGames() {
        // select * from GAME
        return dslContext.selectFrom(GAME).fetch();
    }
}
