package ch.ergon.lernende.wmtippspiel.backend.security.filter;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.CustomAuthentication;
import ch.ergon.lernende.wmtippspiel.backend.security.authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;

    public CustomAuthenticationFilter(@Lazy AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String username = request.getHeader("X-Forwarded-User");
        String email = request.getHeader("X-Forwarded-Mail");

        User user;
        if (username == null || username.equals("") || email == null || email.equals("")) {
            user = User.UNKNOWN_USER;
        } else {
            user = new User(username.trim(), email.trim());
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(user);
        try {
            Authentication result = manager.authenticate(customAuthentication);
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request, response);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
