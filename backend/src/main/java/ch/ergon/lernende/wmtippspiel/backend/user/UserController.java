package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.IamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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

    @GetMapping("/username")
    public ch.ergon.lernende.wmtippspiel.backend.user.User getUsername(Authentication authentication) {
        IamUser userData = (IamUser) authentication.getPrincipal();
        List<UserTO> users = getAllUser();
        User loggedInUser = new User();
        users.forEach(user -> {
            if (Objects.equals(user.getEmail(), userData.mail())) {
                loggedInUser.setFirstName(user.getFirstName());
                loggedInUser.setLastName(user.getLastName());
            }
        });
        return loggedInUser;
    }

    private UserTO convert(User user) {
        UserTO userTO = new UserTO();
        userTO.setId(user.getUserId());
        userTO.setFirstName(user.getFirstName());
        userTO.setLastName(user.getLastName());
        userTO.setEmail(user.getEmail());
        userTO.setPoints(user.getPoints());
        userTO.setAdministrator(user.isAdministrator());
        return userTO;
    }
}
