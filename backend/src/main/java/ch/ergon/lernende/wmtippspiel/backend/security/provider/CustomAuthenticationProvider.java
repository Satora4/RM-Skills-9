package ch.ergon.lernende.wmtippspiel.backend.security.provider;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.CustomAuthentication;
import ch.ergon.lernende.wmtippspiel.backend.security.authentication.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final List<String> ADMIN_USERS = List.of("niculin.steiner@ergon.ch", "joel.vontobel@ergon.ch");

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = (User) authentication.getPrincipal();

        if (user == User.UNKNOWN_USER) {
            throw new BadCredentialsException("unknown user");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        if (isAdmin(user)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN_ROLE"));
        }

        return new CustomAuthentication(user, grantedAuthorities);
    }

    private static boolean isAdmin(User user) {
        return ADMIN_USERS.contains(user.mail());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
