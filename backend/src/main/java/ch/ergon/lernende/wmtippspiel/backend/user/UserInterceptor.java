package ch.ergon.lernende.wmtippspiel.backend.user;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.IamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final String UNKNOWN = "Unbekannt";
    private CurrentUser currentUser;
    private UserRepository userRepository;

    @Autowired
    public UserInterceptor(UserRepository userRepository, CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final IamUser iamUser = (IamUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user = userRepository.getForMail(iamUser.mail());
        if (user == null) {
            final var fullName = inferFullNameFromMailAddress(iamUser.mail());
            userRepository.insertUser(iamUser.mail(), fullName.firstName, fullName.lastName, false);
            user = userRepository.getForMail(iamUser.mail());
        }
        currentUser.setUser(user);

        return true;
    }

    private static FullName inferFullNameFromMailAddress(String mailAddress) {
        final var mailUsernamePart = mailAddress.split("@")[0];
        final var names = mailUsernamePart.split("\\.");
        if (names.length == 2) {
            return new FullName(capitalizeLetterOfString(names[0], 0), capitalizeLetterOfString(names[1], 0));
        } else {
            return new FullName(UNKNOWN, UNKNOWN);
        }
    }

    private static String capitalizeLetterOfString(String string, int idx) {
        return string.substring(0, idx) + string.substring(idx, idx + 1).toUpperCase() + string.substring(idx + 1);
    }

    private record FullName(String firstName, String lastName) {
    }
}
