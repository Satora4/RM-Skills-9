package ch.ergon.lernende.wmtippspiel.backend.user;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.USER;

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
        return dslContext.selectFrom(USER).fetch(userMapper::map);
    }
}
