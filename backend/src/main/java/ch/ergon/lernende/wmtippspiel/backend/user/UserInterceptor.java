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

    private CurrentUser currentUser;
    private UserRepository userRepository;

    @Autowired
    public UserInterceptor(UserRepository userRepository, CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final IamUser iamUser = (IamUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var user =  userRepository.getForMail(iamUser.mail());
        if (user == null) {
            // TODO get first name and last name for ergon user (maybe via adjustment of IAM config?)
            userRepository.insertUser(iamUser.mail(), "Vorname", "Nachname", false);
            user = userRepository.getForMail(iamUser.mail());
        }
        currentUser.setUser(user);

        return true;
    }
}
