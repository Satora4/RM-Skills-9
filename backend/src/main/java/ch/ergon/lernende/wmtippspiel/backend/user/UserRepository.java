package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernende.wmtippspiel.backend.tip.Tip;
import ch.ergon.lernende.wmtippspiel.backend.tip.TipRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

@Repository
public class UserRepository {
    private final TipRepository tipRepository;
    private final DSLContext dslContext;

    @Autowired
    public UserRepository(DSLContext dslContext, TipRepository tipRepository) {
        this.dslContext = dslContext;
        this.tipRepository = tipRepository;
    }

    public List<User> getAllUser() {

        return dslContext.select(
                        USER.USER_ID,
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        USER.EMAIL,
                        USER.POINTS,
                        USER.ADMINISTRATOR)
                .from(USER)
                .groupBy(USER.USER_ID)
                .orderBy(USER.POINTS.desc())
                .fetchInto(User.class);
    }

    public List<User> getAllUserWithTips() {
        List<Tip> tips = tipRepository.getAllTip();
        List<User> users = getAllUser();
        List<User> userWithTips = new ArrayList<>();
        users.forEach(user -> {
            tips.stream()
                    .filter(tip -> tip.getUser().equals(user))
                    .findFirst()
                    .ifPresent(userTip -> userWithTips.add(user));
        });

        return userWithTips;
    }

    public User getForMail(String mail) {

        return dslContext.select(
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        USER.USER_ID,
                        USER.EMAIL,
                        USER.POINTS,
                        USER.ADMINISTRATOR)
                .from(USER)
                .where(USER.EMAIL.eq(mail))
                .fetchOneInto(User.class);
    }

    public void updateUser(User user) {
        dslContext.update(USER)
                .set(USER.POINTS, user.getPoints())
                .where(USER.USER_ID.eq(user.getUserId()))
                .execute();
    }

    public void insertUser(String email, String firstName, String lastName, boolean isAdmin) {
        dslContext.insertInto(USER)
                .set(USER.EMAIL, email)
                .set(USER.FIRST_NAME, firstName)
                .set(USER.LAST_NAME, lastName)
                .set(USER.POINTS, 0)
                .set(USER.ADMINISTRATOR, isAdmin)
                .execute();
    }
}
