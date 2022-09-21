package ch.ergon.lernende.wmtippspiel.backend.user;

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

        return dslContext.select(
                        USER.USER_ID.as("id"),
                        USER.FIRST_NAME.as("firstName"),
                        USER.LAST_NAME.as("lastName"),
                        USER.EMAIL.as("email"),
                        USER.POINTS.as("points"))
                .from(USER)
                .groupBy(USER.USER_ID)
                .orderBy(USER.POINTS.desc())
                .fetchInto(User.class);
    }
}
