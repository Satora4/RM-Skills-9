package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.UserRecord;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User map(UserRecord userRecord) {
        User user = new User();

        user.setId(userRecord.getUserId());
        user.setFirstName(userRecord.getFirstName());
        user.setLastName(userRecord.getLastName());
        user.setEmail(userRecord.getEmail());
        user.setRanking(userRecord.getRanking());
        user.setAdministrator(userRecord.getAdministrator());

        return user;
    }
}
