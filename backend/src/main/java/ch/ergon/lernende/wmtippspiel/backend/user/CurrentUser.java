package ch.ergon.lernende.wmtippspiel.backend.user;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
