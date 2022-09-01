package ch.ergon.lernende.wmtippspiel.backend.security.provider;

import ch.ergon.lernende.wmtippspiel.backend.security.authentication.CustomAuthentication;
import ch.ergon.lernende.wmtippspiel.backend.security.authentication.User;
import org.springframework.security.authentication.AuthenticationProvider;
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
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        var userRole = new SimpleGrantedAuthority("USER_ROLE");
        grantedAuthorities.add(userRole);

        if (isAdmin(user)) {
            var adminRole = new SimpleGrantedAuthority("ADMIN_ROLE");
            grantedAuthorities.add(adminRole);
        }
        return new CustomAuthentication(user, grantedAuthorities);
    }

    private static boolean isAdmin(User user) {
        return ADMIN_USERS.contains(user.getMail());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
