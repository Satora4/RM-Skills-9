package ch.ergon.lernende.wmtippspiel.backend.user;

import org.jooq.DSLContext;
import org.jooq.Record;
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
                        USER.USER_ID,
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        USER.EMAIL,
                        USER.POINTS)
                .from(USER)
                .groupBy(USER.USER_ID)
                .orderBy(USER.POINTS.desc())
                .fetchInto(User.class);
    }

    public User getForMail(String mail) {

        return dslContext.select(
                        USER.USER_ID,
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        USER.EMAIL,
                        USER.POINTS,
                        USER.ADMINISTRATOR)
                .from(USER)
                .where(USER.EMAIL.eq(mail))
                .fetchOne(this::convert);
    }

    public void updateUser(User user) {
        
        dslContext.update(USER)
                .set(USER.POINTS, user.getPoints())
                .where(USER.USER_ID.eq(user.getId()))
                .execute();
    }

    private User convert(Record record) {
        User user = new User();
        user.setId(record.get(USER.USER_ID));
        user.setFirstName(record.get(USER.FIRST_NAME));
        user.setLastName(record.get(USER.LAST_NAME));
        user.setPoints(record.get(USER.POINTS));
        user.setEmail(record.get(USER.EMAIL));
        user.setAdministrator(record.get(USER.ADMINISTRATOR));

        return user;
    }
}
