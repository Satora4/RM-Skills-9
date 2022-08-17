package ch.ergon.lernende.wmtippspiel.backend.user;

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

    private UserTO convert(User user) {
        UserTO userTO = new UserTO();
        userTO.setId(user.getId());
        userTO.setFirstName(user.getFirstName());
        userTO.setLastName(user.getLastName());
        userTO.setEmail(user.getEmail());
        userTO.setRanking(user.getRanking());
        userTO.setAdministrator(user.isAdministrator());
        return userTO;
    }
}
