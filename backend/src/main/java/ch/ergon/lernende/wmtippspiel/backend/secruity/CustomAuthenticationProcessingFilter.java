package ch.ergon.lernende.wmtippspiel.backend.secruity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    public CustomAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                                AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher);
        //Set authentication manager
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // Extract from request
        String user = request.getHeader("X-Forwarded-User");
        String mail = request.getHeader("X-Forwarded-Mail");
        // Create a token object ot pass to Authentication Provider
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(user, null);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws ServletException, java.io.IOException {
        // Save user principle in security context
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
