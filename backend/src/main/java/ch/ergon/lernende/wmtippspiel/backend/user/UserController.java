package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.IamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/allUser")
    public List<UserTO> getAllUser() {
        return userRepository.getAllUser().stream().map(this::convert).collect(Collectors.toList());
    }

    @GetMapping("/user")
    public UserTO getUser(Authentication authentication) {
        IamUser userData = (IamUser) authentication.getPrincipal();

        return convert(userRepository.getUser(userData));
    }

    private UserTO convert(User user) {
        UserTO userTO = new UserTO();
        userTO.setUserId(user.getUserId());
        userTO.setFirstName(user.getFirstName());
        userTO.setLastName(user.getLastName());
        userTO.setEmail(user.getEmail());
        userTO.setPoints(user.getPoints());
        userTO.setAdministrator(user.isAdministrator());
        return userTO;
    }
}
