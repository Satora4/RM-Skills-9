package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public List<UserTO> getAllUser() {
        return userRepository.getAllUser().stream().map(this::convert).collect(Collectors.toList());
    }

    private UserTO convert(UserRecord userRecord) {
        UserTO userTO = new UserTO();
        userTO.setId(userRecord.getUserId());
        userTO.setFirstName(userRecord.getFirstName());
        userTO.setLastName(userRecord.getLastName());
        userTO.setEmail(userRecord.getEmail());
        userTO.setPoints(userRecord.getPoints());
        userTO.setRanking(userRecord.getRanking());
        userTO.setAdministrator(userRecord.getAdministrator());
        return userTO;
    }
}
