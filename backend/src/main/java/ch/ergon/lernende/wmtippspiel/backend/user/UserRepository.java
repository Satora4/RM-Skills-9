package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.USER;

@Repository
public class UserRepository {

    private final DSLContext dslContext;

    @Autowired
    public UserRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<User> getAllUser() {
        return dslContext.selectFrom(USER).fetch(this::convert);
    }

    private User convert(UserRecord userRecord) {
        User user = new User();

        user.setId(userRecord.getUserId());
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setEmail(userRecord.getEmail());
        user.setPoints(userRecord.getPoints());
        user.setRanking(userRecord.getRanking());
        user.setAdministrator(userRecord.getAdministrator());

        return user;
    }
}
