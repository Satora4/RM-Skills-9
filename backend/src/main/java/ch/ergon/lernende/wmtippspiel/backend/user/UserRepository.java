package ch.ergon.lernende.wmtippspiel.backend.user;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.TIP;
import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.USER;
import static org.jooq.impl.DSL.sum;

@Repository
public class UserRepository {

    private final DSLContext dslContext;
    private final UserMapper userMapper;

    @Autowired
    public UserRepository(DSLContext dslContext, UserMapper userMapper) {
        this.dslContext = dslContext;
        this.userMapper = userMapper;
    }

    public List<User> getAllUser() {

        return dslContext.select(
                        USER.USER_ID.as("id"),
                        USER.FIRST_NAME.as("firstName"),
                        USER.LAST_NAME.as("lastName"),
                        USER.EMAIL.as("email"),
                        USER.RANKING.as("ranking"),
                        sum(TIP.POINTS).as("points"))
                .from(USER)
                .leftJoin(TIP)
                .on(USER.USER_ID.eq(TIP.USER_ID))
                .groupBy(USER.USER_ID)
                .fetchInto(User.class);
    }
}
